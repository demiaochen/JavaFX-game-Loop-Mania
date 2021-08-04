# Assumptions 

## Character stats
- Character will have Hit points(HP), Attack and Defence stats. 
- Character will spawn with full HP which is relatively high compared to enemies spawning in early levels to give
the player a chance to accumulate exp and gold. 
- Defence is used in damage calculation to mitigate some damage recieved so that the player can make a meaningful
decision to invest into more Attack or Defence stats

## Allied soldiers
- Will have HP, Attack and Defence stats.
- Will have HP 200 , Attack 50  and Defence 0.
- Will be lower than the Character and similar to enemies as they are only support characters and should not be able to "win" the game by itself. 
- Supports the character to allow the player to make the meaningful decision to place Buildings to spawn more.

## Enemies
- Will have Hp, Attack, Defence, Attack radius, Support radius, movement speed
- Will have a Variety of stat values in order to allow for meaningful combat and build decisions by the player
- Will have a Variety of drops eg. gold, items, exp, Cards
    - small chance of dropping rare items
- Variety of enemies for the Character to engage in combat with, giving the player agency over their building decisions,whether to build more enemy buildings to generate more gold or build more allied buildings to assist etc.
- Variety of enemies for meaningful encounters
### Vampire
- Can critically strike the Character to deal more damage to introduce element of chance
- If Vampire moves into the range of a campfire, the Vampire will instantly move to closest tile outside of the range and continue movement
### Zombie
- Have 100 hp, 150 damage.
- If defeats an allied soldiers, 30% chance will provoke convert bite, which causes soldiers turn into a full health Zombie and continue combat against player.

## Shop
- items are priced on a scale so that higher value items cannot be purchased immediately to give players agency over what to purchase at the end of every round.
- If Character's backpack is full, Character cannot buy anything from shop.

## Movement
- if moving into a square with barraks and enemy is also within range, attatch the ally character before combat
- movement should be tick based, similar to btd?
- Character will follow the path in a clock-wise fashion around the circuit towards the hero's castle
- enemies will move in an anti-clockwise fashion in order for more engagements to occur between the character

## Combat
- Combat will only end if all enemies being defeated or character's HP is below 0.
- Each entity in a battle including character, soldiers, enemies will attack in turn, with priority sequence: character, soldiers, enemies. Character and soldiers will stick to attack one enemy until it is killed, then attack the next enemy. Enemies will attack soldiers first, stick to attack one soldier until it is killed, then attack the next soldier. Enemies will not attack character until no soldiers alive.
- No ui-element
    - Character will continue moving (removing Enemy sprites) if combat is successful
    - else Character will die and game will end
    - Thus removing clutter from screen for a satisfying ui experience
- User get exp and gold reward after winning a battle, and has a chance to get cards and equipment, probability depends on type of enemies.

## Goals

- To end and Win the game the player should kill all bosses or achieve 50 game cycles or acquire 100000 exp which is accumulated by defeating enemies. Thus offering the player a chance to take different strategies to defeating the game.


## Items
- 10 Max. The quantity limitations on item can increase the difficulty of game, and make bag clean.
- First dropped first picked
- Dropped item will give user gold and exp reward 

### Health potions
- Character can hold unlimited amount. This makes character safe, and user will hence not always worried about consuming holding potions when character's health is safe. 
- Consumed immediately after using, recover hero's full health.

### Anduril and Tree Trump
- 1/5 chance get when character getting a equipment reward from a battle
### The One Ring 
- Only can obtain one in a game
- 1% chance drop a battle
- When user have a Ring, will appear "Gothca" besides Ring pic in UI 
- If the Character is killed with The One Ring hold, it respawns with full health up to a single time

## Cards

- 8 Max. To give users some pressure to use his/her cards, instead of storing them in bag.
- First dropped first picked
- Dropped cards will give user gold and exp reward 

## Buildings
- After being placed they will remain within their own tile for the rest of the game performing set actions, thus giving player options for what they can place -> enhancing game variety

## World State

- Ui Updated everytime there is combat, movement, Item used, building constructed

## Mode
- There will be four different modes: Standard, Survival, Berserker, Confusing
- In survival mode, the Human Player can only purchase 1 health potion each time the Character shops at the Hero's Castle.
In berserker mode, the Human Player cannot purchase more than 1 piece of protective gear (protective gear includes armour, helmets, and shields) each time it shops at the Hero's Castle.
Standard mode has no distinguishing effects.
- In confusing mode, user will get one of two other rare items' effect when getting one of the three rare items
- Current mode will be shown in Application's title

## Win and Lose
- Text and exit button will popup in the middle, user can only exit the game or check trophies at this stage.


## Milestone2
- Character, soldiers and buildings attack before all enemies, which makes the game easier.
- If an enemy do a critical strike, it still can do its basic attack for this round.
- Only one battle start for one character's movement, to minimise bugs and clear logic.
- "Adjacent" means 4 directions (top, bottom, left, right).
- Press 'P' to pause the game, "spacebar" will cause bug.
- When character takes damage, real damage equals (damage-defense)/(1-percentage of damage reduce).
- Can build mutiple buildings on one gird, effects of buildings will accumulate.
- Character will only do double damage even within range of mutiple campfire, cause too high damage for square accumulation.
- Press 'H' to consume a health potion to reduce use of mouse.

## Milestone3
- Player can get a doggie pet after defeating the Doggie for first time, so the game will be more interesting.
- The chance of getting cards and equipments from enemies is reduced to improve the difficulty of game.
- Magic will only kill alive enemies, except the boss, to make the game more challenge.
- Doggie pet can't be attacked and is very weak, because it is just for fun.