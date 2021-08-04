package unsw.loopmania.entity.movingEntity.basicAlly;

import unsw.loopmania.util.PathPosition;

/**
 * A pet the main character can acquire which will help with battles
 */
public class DoggiePet extends BasicAlly{
    public DoggiePet(PathPosition position) {
        super(position, 100, 5, 0);
    }
}
