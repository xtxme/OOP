public class Lab03 {

    // Abstract class for Equipment
    public static abstract class Equipment {
        protected double baseEffect; // ดาเมจพื้นฐานหรือค่าการป้องกันพื้นฐาน
        protected int level; // ระดับของอุปกรณ์
        protected double baseSpeedDecrease; // ค่าที่ลดความเร็วพื้นฐาน

        public Equipment(double baseEffect, int level, double baseSpeedDecrease) {
            this.baseEffect = baseEffect;
            this.level = level;
            this.baseSpeedDecrease = baseSpeedDecrease;
        }

        public void levelUp() {
            level++;
        }

        public abstract double calEffect(); // คำนวณค่าดาเมจหรือป้องกัน

        public double calSpeedDecrease() {
            return baseSpeedDecrease * (190 + 0.13 * level);
        }
    }

    // Sword class
    public static class Sword extends Equipment {
        public Sword(double baseDamage, int level, double baseSpeedDecrease) {
            super(baseDamage, level, baseSpeedDecrease);
        }

        @Override
        public double calEffect() {
            return baseEffect * (7 + 0.13 * level); // คำนวณดาเมจ
        }
    }

    // Shield class
    public static class Shield extends Equipment {
        public Shield(double baseDefense, int level, double baseSpeedDecrease) {
            super(baseDefense, level, baseSpeedDecrease);
        }

        @Override
        public double calEffect() {
            return baseEffect * (7 + 0.13 * level); // คำนวณค่าการป้องกัน
        }
    }

    // RPGCharacter class
    public static class RPGCharacter {
        private int level;
        private double maxHP;
        private double maxMana;
        private double currentHP;
        private double currentMana;
        private double baseSpeed;
        private double currentSpeed;
        private Sword equippedSword;
        private Shield equippedShield;

        public RPGCharacter(int level, double baseSpeed) {
            this.level = level;
            this.baseSpeed = baseSpeed;
            recalStats(); // คำนวณค่าต่างๆ
            this.currentHP = maxHP;
            this.currentMana = maxMana;
        }

        public void levelUp() {
            level++;
            recalStats();
        }

        public void useSword(Sword sword) {
            this.equippedSword = sword;
            adjustSpeed();
        }

        public void useShield(Shield shield) {
            this.equippedShield = shield;
            adjustSpeed();
        }

        public void takeDamage(double damage) {
            double reducedDamage = damage;
            if (equippedShield != null) {
                reducedDamage -= equippedShield.calEffect();
            }
            currentHP = Math.max(0, currentHP - reducedDamage);
        }

        public void recalStats() {
            maxHP = 100 + 10 * level;
            maxMana = 100 + 2 * level;
            currentSpeed = baseSpeed * (190 + 0.03 * level);
            adjustSpeed();
        }

        public void adjustSpeed() {
            double speedPenalty = 0;
            if (equippedSword != null) {
                speedPenalty += equippedSword.calSpeedDecrease();
            }
            if (equippedShield != null) {
                speedPenalty += equippedShield.calSpeedDecrease();
            }
            currentSpeed = Math.max(0, baseSpeed * (90 + 0.13 * level) - speedPenalty);
        }

        public double getMaxHP() {
            return maxHP;
        }

        public double getCurrentHP() {
            return currentHP;
        }

        public double getCurrentSpeed() {
            return currentSpeed;
        }

        public int getLevel() {
            return level;
        }
    }

    public static void main(String[] args) {
        RPGCharacter character = new RPGCharacter(1, 5.0);
        Sword sword = new Sword(10.0, 1, 0.5);
        Shield shield = new Shield(8.0, 1, 0.4);

        System.out.println("[ At the Beginning ]");
        System.out.println("Level_Player: " + character.getLevel());
        System.out.println("Max HP: " + character.getMaxHP());
        System.out.println("Current HP: " + character.getCurrentHP());
        System.out.println("Current Mana: " + character.currentMana);
        System.out.println("Current Speed: " + character.getCurrentSpeed());
        System.out.println("Level_Sword: " + sword.level);
        System.out.println("Level_Shield: " + shield.level);

        character.useSword(sword);
        character.useShield(shield);

        System.out.println("\n[ After wearing Sword and Shield ]");
        System.out.println("Level_Player: " + character.getLevel());
        System.out.println("Current Speed: " + character.getCurrentSpeed());
        System.out.println("Sword Damage: " + sword.calEffect());
        System.out.println("Shield Defense: " + shield.calEffect());
        System.out.println("Level_Sword: " + sword.level);
        System.out.println("Level_Shield: " + shield.level);

        character.takeDamage(50);
        System.out.println("\n[ After Taking Attacked ]");
        System.out.println("Level_Player: " + character.getLevel());
        System.out.println("Current HP: " + character.getCurrentHP());
        System.out.println("Current Mana: " + character.currentMana);
        System.out.println("Current Speed: " + character.getCurrentSpeed());
        System.out.println("Level_Sword: " + sword.level);
        System.out.println("Level_Shield: " + shield.level);

        sword.levelUp();
        shield.levelUp();

        System.out.println("\n[ After Leveling Up Equipment ]");
        System.out.println("Level_Player: " + character.getLevel());
        System.out.println("Sword Damage: " + sword.calEffect());
        System.out.println("Shield Defense: " + shield.calEffect());
        System.out.println("Level_Sword: " + sword.level);
        System.out.println("Level_Shield: " + shield.level);
        System.out.println("Current Speed: " + character.getCurrentSpeed());

        character.levelUp();
        System.out.println("\n[ After Leveling Up Character ]");
        System.out.println("Level_Player: " + character.getLevel());
        System.out.println("Max HP: " + character.getMaxHP());
        System.out.println("Current HP: " + character.getCurrentHP());
        System.out.println("Current Mana: " + character.currentMana);
        System.out.println("Current Speed: " + character.getCurrentSpeed());
        System.out.println("Sword Damage: " + sword.calEffect());
        System.out.println("Shield Defense: " + shield.calEffect());
        System.out.println("Level_Sword: " + sword.level);
        System.out.println("Level_Shield: " + shield.level);
    }
}

