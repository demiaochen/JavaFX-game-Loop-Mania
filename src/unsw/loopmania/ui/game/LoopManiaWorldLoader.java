package unsw.loopmania.ui.game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import org.javatuples.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.entity.Entity;
import unsw.loopmania.entity.movingEntity.basicAlly.Character;
import unsw.loopmania.entity.staticEntity.PathTile;
import unsw.loopmania.entity.staticEntity.building.buildingOnPath.CastleBuilding;
import unsw.loopmania.util.PathPosition;

import java.util.List;

/**
 * Loads a world from a .json file.
 * 
 * By extending this class, a subclass can hook into entity creation.
 * This is useful for creating UI elements with corresponding entities.
 * 
 * this class is used to load the world.
 * it loads non-spawning entities from the configuration files.
 * spawning of enemies/cards must be handled by the controller.
 */
public abstract class LoopManiaWorldLoader {
    private JSONObject json;

    public LoopManiaWorldLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("worlds/" + filename)));
    }

    /**
     * Parses the JSON to create a world.
     */
    public LoopManiaWorld load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        // path variable is collection of coordinates with directions of path taken...
        List<Pair<Integer, Integer>> orderedPath = loadPathTiles(json.getJSONObject("path"), width, height);

        LoopManiaWorld world = new LoopManiaWorld(width, height, orderedPath);

        JSONObject goal = json.getJSONObject("goal-condition");
        loadGoal(world, goal);

        loadRareItems(world);

        JSONArray jsonEntities = json.getJSONArray("entities");

        // load non-path entities later so that they're shown on-top
        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(world, jsonEntities.getJSONObject(i), orderedPath);
        }

        return world;
    }

    /**
     * load an entity into the world
     * @param world backend world object
     * @param json a JSON object to parse (different from the )
     * @param orderedPath list of pairs of x, y cell coordinates representing game path
     */
    private void loadEntity(LoopManiaWorld world, JSONObject currentJson, List<Pair<Integer, Integer>> orderedPath) {
        String type = currentJson.getString("type");
        int x = currentJson.getInt("x");
        int y = currentJson.getInt("y");
        int indexInPath = orderedPath.indexOf(new Pair<Integer, Integer>(x, y));
        assert indexInPath != -1;

        Entity entity = null;
        // TODO = load more entity types from the file
        switch (type) {
        case "character":
            Character character = new Character(new PathPosition(indexInPath, orderedPath));
            world.setCharacter(character);
            onLoad(character);
            entity = character;
            break;
        case "path_tile":
            throw new RuntimeException("path_tile's aren't valid entities, define the path externally.");
        case "hero_castle":
            SimpleIntegerProperty interger = new SimpleIntegerProperty();
            interger.set(0);
            CastleBuilding castle = new CastleBuilding(interger, interger);
            onLoad(castle);
            entity = castle;
            break;
        // TODO Handle other possible entities
        }
        world.addEntity(entity);
    }

    /**
     * load path tiles
     * @param path json data loaded from file containing path information
     * @param width width in number of cells
     * @param height height in number of cells
     * @return list of x, y cell coordinate pairs representing game path
     */
    private List<Pair<Integer, Integer>> loadPathTiles(JSONObject path, int width, int height) {
        if (!path.getString("type").equals("path_tile")) {
            // ... possible extension
            throw new RuntimeException(
                    "Path object requires path_tile type.  No other path types supported at this moment.");
        }
        PathTile starting = new PathTile(new SimpleIntegerProperty(path.getInt("x")), new SimpleIntegerProperty(path.getInt("y")));
        if (starting.getY() >= height || starting.getY() < 0 || starting.getX() >= width || starting.getX() < 0) {
            throw new IllegalArgumentException("Starting point of path is out of bounds");
        }
        // load connected path tiles
        List<PathTile.Direction> connections = new ArrayList<>();
        for (Object dir: path.getJSONArray("path").toList()){
            connections.add(Enum.valueOf(PathTile.Direction.class, dir.toString()));
        }

        if (connections.size() == 0) {
            throw new IllegalArgumentException(
                "This path just consists of a single tile, it needs to consist of multiple to form a loop.");
        }

        // load the first position into the orderedPath
        PathTile.Direction first = connections.get(0);
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<>();
        orderedPath.add(Pair.with(starting.getX(), starting.getY()));

        int x = starting.getX() + first.getXOffset();
        int y = starting.getY() + first.getYOffset();

        // add all coordinates of the path into the orderedPath
        for (int i = 1; i < connections.size(); i++) {
            orderedPath.add(Pair.with(x, y));
            
            if (y >= height || y < 0 || x >= width || x < 0) {
                throw new IllegalArgumentException("Path goes out of bounds at direction index " + (i - 1) + " (" + connections.get(i - 1) + ")");
            }
            
            PathTile.Direction dir = connections.get(i);
            PathTile tile = new PathTile(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
            x += dir.getXOffset();
            y += dir.getYOffset();
            if (orderedPath.contains(Pair.with(x, y)) && !(x == starting.getX() && y == starting.getY())) {
                throw new IllegalArgumentException("Path crosses itself at direction index " + i + " (" + dir + ")");
            }
            onLoad(tile, connections.get(i - 1), dir);
        }
        // we should connect back to the starting point
        if (x != starting.getX() || y != starting.getY()) {
            throw new IllegalArgumentException(String.format(
                    "Path must loop back around on itself, this path doesn't finish where it began, it finishes at %d, %d.",
                    x, y));
        }
        onLoad(starting, connections.get(connections.size() - 1), connections.get(0));
        return orderedPath;
    }

    /**
     * load goals in json file
     * @param world
     * @param goal
     */
    private void loadGoal(LoopManiaWorld world, JSONObject goal) {
        String condition1 = goal.getString("goal");
        JSONArray goals = goal.getJSONArray("subgoals");
        int exp = 0;
        int cycle = 0;
        String condition2 = "";
        boolean killAllBosses = false;
        
        for(int i = 0; i < goals.length(); i++) {
            String goalType = goals.getJSONObject(i).getString("goal");
            int amount = 0;
            if(goalType.equals("experience")) {
                amount = goals.getJSONObject(i).getInt("quantity");
                exp = amount;
            } else if(goalType.equals("cycles")) {
                cycle = amount;
            } else {
                condition2 = goalType;
                JSONArray goals2 = goals.getJSONObject(i).getJSONArray("subgoals");
                for(int j = 0; j < goals2.length(); j++) {
                    goalType = goals2.getJSONObject(j).getString("goal");
                    if(goalType.equals("cycles")) {
                        amount = goals2.getJSONObject(j).getInt("quantity");
                        cycle= amount;
                    } else if(goalType.equals("bosses")) {
                        killAllBosses = true;
                    }
                }
            }
        }

        world.setGoal(cycle, exp, killAllBosses, condition1, condition2);
    }

    private void loadRareItems(LoopManiaWorld world) {
        JSONArray rare = json.getJSONArray("rare_items");
        List<String> rareItems = new ArrayList<String>();
        for(int i = 0; i < rare.length(); i++) {
            rareItems.add(rare.getString(i));
        }
        if(rareItems.contains("the_one_ring")) {
            world.setOneRingAvailable(true);
        } else {
            world.setOneRingAvailable(false);
        }

        if(rareItems.contains("anduril")) {
            world.setAndurilAvailable(true);
        } else {
            world.setAndurilAvailable(false);
        }

        if(rareItems.contains("stump")) {
            world.setTreeStump(true);
        } else {
            world.setTreeStump(false);
        }
    }

    public abstract void onLoad(Character character);
    public abstract void onLoad(PathTile pathTile, PathTile.Direction into, PathTile.Direction out);

    // TODO Create additional abstract methods for the other entities
    public abstract void onLoad(CastleBuilding castle);

}
