public class Lab05 {
    public static abstract class Character {
        String name;
        String characterClass;
        int level;
        int maxHP;
        int maxMana;
        int currentHP;
        int currentMana;
        int ATKPower;
        int DEF;
        int SPD;
        Accessory necklace;
        Accessory bracelet;

        public Character(String name, String characterClass) {
            this.name = name;
            this.characterClass = characterClass;
            this.level = 1;
            this.maxHP = 100;
            this.maxMana = 50;
            this.currentHP = maxHP;
            this.currentMana = maxMana;
            this.ATKPower = 20;
            this.DEF = 10;
            this.SPD = 15;
        }

        /**
         * @param: none
         * @return: currentMana
         * @effects: none
         */

        public int getCurrentMana() {
            return currentMana;
        }

        public int getCurrentHP() {
            return currentHP;
        }

        /**
         *
         * @return
         */
        public String getName() {
            return name;
        }
        /**
         * @param:
         * @return:
         * effects:
         */
        public void levelUp() {
            this.level++;
            recalStats();
            System.out.println(name + " leveled up to level " + level + "!");
        }

        /**
         * @param: Character must be created first
         * @return: Character level must be greater than 0,
                    MaxHP, MaxMana, ATKPower, DEF, SPD stats are updated based on current level
                    CurrentHP and currentMana are reset to maxHP and maxMana
         * effects: Print message indicating updated stats
         */

        /**
         *
         */
        public void recalStats() {
            maxHP += 20 * level;
            maxMana += 10 * level;
            ATKPower += 5 * level;
            DEF += 2 * level;
            SPD += level;
            currentHP = maxHP;
            currentMana = maxMana;
            System.out.println(name + " recalculated stats.");

        }

        public void adjustSpeed() {
            if (necklace != null) {
                SPD += necklace.calEffect() / 10; // เพิ่มสปีดตามผลของสร้อยคอ
            }
            if (bracelet != null) {
                SPD -= bracelet.calEffect() / 10; // ลดสปีดตามผลของกำไล
            }
            System.out.println(name + "'s speed adjusted to " + SPD);
        }

        public void equipAccessory(Accessory accessory) {
            if (accessory instanceof Necklace) {
                this.necklace = accessory;
                System.out.println(name + " equipped a Necklace.");
            } else if (accessory instanceof Bracelets) {
                this.bracelet = accessory;
                System.out.println(name + " equipped a Bracelet.");
            }
            adjustSpeed();
        }

        public void ATK(Character target) {
            int damage = Math.max(0, ATKPower - target.DEF); // ดาเมจไม่ต่ำกว่า 0
            target.currentHP = Math.max(0, target.currentHP - damage); // HP ไม่ต่ำกว่า 0
            System.out.println(name + " attacks " + target.name + " dealing " + damage + " damage");
        }

        public void useAbility(String ability, Character target) {
            if (currentMana >= 10) {
                int damage = ATKPower * 2; // ความเสียหายจากความสามารถ
                target.currentHP = Math.max(0, target.currentHP - damage); // HP ไม่ต่ำกว่า 0
                currentMana -= 10; // ลดมานาหลังใช้ความสามารถ
                System.out.println(name + " uses ability " + ability + " dealing " + damage + " damage to " + target.name);
            } else {
                System.out.println(name + " does not have enough mana to use " + ability);
            }
        }
    }

    // Mage Interface
    public interface Mage {
        void castSpell(String spellName, Character target);
        int calManaCost(String spellName);
        void amplifySpell(String spellName);
    }

    // Tank Interface
    public interface Tank {
        void fortifyArmor(int amount);
        void absorbDamage(Character ally);
        void repairArmor(int amount);
    }

    // Mage Implementation
    public static class MageCharacter extends Character implements Mage {
        private String elementalMagic;

        public MageCharacter(String name, String mage, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, String iceMagic) {
            super(name, "Mage");
            this.ATKPower = 40;
            this.DEF = 10;
        }

        public String getElementalMagic() {
            return elementalMagic;
        }

        public void setElementalMagic(String elementalMagic) {
            this.elementalMagic = elementalMagic;
            System.out.println(name + " changed elemental magic to " + elementalMagic + ".");
        }

        @Override
        public void castSpell(String spellName, Character target) {
            int manaCost = calManaCost(spellName);
            if (currentMana >= manaCost) {
                int spellDamage = ATKPower * 3;
                target.currentHP = Math.max(0, target.currentHP - spellDamage);
                currentMana -= manaCost;
                System.out.println(name + " casts " + spellName + " (" + elementalMagic + ") dealing " + spellDamage + " damage to " + target.name);
            } else {
                System.out.println(name + " does not have enough mana to cast " + spellName);
            }
        }

        @Override
        public int calManaCost(String spellName) {
            switch (spellName.toLowerCase()) {
                case "fireball":
                    return 15;
                case "ice spike":
                    return 10;
                case "lightning bolt":
                    return 20;
                default:
                    return 5; // Default mana cost for unknown spells
            }
        }

        @Override
        public void amplifySpell(String spellName) {
            ATKPower += 5;
            System.out.println(name + " amplifies " + spellName + ", increasing attack power to " + ATKPower);
        }
    }

    // Tank Implementation
    public static class TankCharacter extends Character implements Tank {
        public TankCharacter(String name, String tank, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            super(name, "Tank");
            this.ATKPower = 10;
            this.DEF = 15;
        }

        @Override
        public void fortifyArmor(int amount) {
            DEF += amount;
            System.out.println(name + " fortifies armor by " + amount + ". Current DEF: " + DEF);
        }

        @Override
        public void absorbDamage(Character ally) {
            System.out.println(name + " absorbs damage for " + ally.name);
            ally.DEF += this.DEF / 2;
        }

        @Override
        public void repairArmor(int amount) {
            DEF += amount;
            System.out.println(name + " repairs armor by " + amount + ". Current DEF: " + DEF);
        }
    }

    // Accessory Class
    public static abstract class Accessory {
        String accessoryClass;
        int level;
        int baseSpeedForDecrease;
        int baseSpeedForIncrease;
        int calEffect;

        public Accessory(String accessoryClass) {
            this.accessoryClass = accessoryClass;
            this.level = 1;  // Initial level
            this.baseSpeedForDecrease = 0;  // Default value
            this.baseSpeedForIncrease = 0;  // Default value
            this.calEffect = 0;  // Default effect
        }

        public String getAccessoryClass() {
            return accessoryClass;
        }

        public int getLevel() {
            return level;
        }

        public int calEffect() {
            return calEffect;
        }

        public void levelUp() {
            level++;
            adjustStatsForLevelUp();
            System.out.println(accessoryClass + " leveled up to level " + level + "!");
        }

        public int calSpeedDecrease() {
            return baseSpeedForDecrease * level;
        }

        public int calSpeedIncrease() {
            return baseSpeedForIncrease * level;
        }

        protected abstract void adjustStatsForLevelUp();
    }

    // Necklace Implementation
    public static class Necklace extends Accessory {
        private int damageBonus;

        public Necklace() {
            super("Necklace");
            this.damageBonus = 10;  // Default damage bonus
            this.baseSpeedForDecrease = 1;  // Minor decrease in speed for Necklace
            this.baseSpeedForIncrease = 2;  // Minor increase in speed for Necklace
        }

        @Override
        protected void adjustStatsForLevelUp() {
            // เพิ่มค่าพลังโจมตีเมื่ออุปกรณ์เลเวลสูงขึ้น
            this.damageBonus += 5 * level;
            this.calEffect = damageBonus;
        }

        @Override
        public int calEffect() {
            return damageBonus;  // ค่าพลังโจมตีที่เพิ่มขึ้นจากสร้อยคอ
        }

        public void levelUp() {
            level++;  // เพิ่มระดับของสร้อยคอ
            adjustStatsForLevelUp();  // ปรับปรุงสเตตัสหลังจากเพิ่มระดับ
            System.out.println("Necklace leveled up to level " + level + "!");
        }

        public int getDamage() {
            return damageBonus;  // คำนวณดาเมจจากสร้อยคอ
        }
    }

    // Bracelets Implementation
    public static class Bracelets extends Accessory {
        private int defenseBonus;

        public Bracelets() {
            super("Bracelet");
            this.defenseBonus = 5;  // Default defense bonus
            this.baseSpeedForDecrease = 2;  // Moderate decrease in speed for Bracelet
            this.baseSpeedForIncrease = 1;  // Minor increase in speed for Bracelet
        }

        @Override
        protected void adjustStatsForLevelUp() {
            // เพิ่มค่าการป้องกันเมื่ออุปกรณ์เลเวลสูงขึ้น
            this.defenseBonus += 3 * level;
            this.calEffect = defenseBonus;
        }

        @Override
        public int calEffect() {
            return defenseBonus;  // ค่าการป้องกันที่เพิ่มขึ้นจากกำไล
        }

        public void levelUp() {
            level++;  // เพิ่มระดับของกำไล
            adjustStatsForLevelUp();  // ปรับปรุงสเตตัสหลังจากเพิ่มระดับ
            System.out.println("Bracelet leveled up to level " + level + "!");
        }

        public int getDefense() {
            return defenseBonus;  // คำนวณการป้องกันจากกำไล
        }
    }

    public static void main(String[] args) {
        // สร้างตัวละคร
        MageCharacter mage = new MageCharacter("Mage", "Mage", 1, 100, 50, 100, 50, 20, 5, 10, 3, "Ice Magic");
        TankCharacter tank = new TankCharacter("Tank", "Tank", 1, 200, 30, 150, 20, 10, 15, 5, 2, 100);

        // สร้างอุปกรณ์เสริม
        Necklace necklace = new Necklace();
        Bracelets bracelet = new Bracelets();

        // สวมอุปกรณ์เสริม
        System.out.println("Put on accessories...");
        mage.equipAccessory(necklace);
        tank.equipAccessory(bracelet);
        System.out.println(mage.getName() + " equips Necklace with damage bonus!");
        System.out.println(tank.getName() + " equips Bracelet with defense bonus!");
        System.out.println();

        // แสดงสเตตัสเริ่มต้นของทั้งสองตัวละคร
        System.out.println("Initial Stats:");
        System.out.println(mage.getName() + " - HP: " + mage.getCurrentHP() + ", Mana: " + mage.getCurrentMana());
        System.out.println(tank.getName() + " - HP: " + tank.getCurrentHP() + ", Mana: " + tank.getCurrentMana());
        System.out.println();

        // ทำให้ตัวละครเพิ่มเลเวล
        System.out.println("Level up characters...");
        mage.levelUp();
        tank.levelUp();
        System.out.println();

        // เริ่มการต่อสู้
            System.out.println("----- New Round -----");

            // Mage โจมตี Tank
            System.out.println(mage.getName() + " attacks " + tank.getName() + " with basic attack!");
            mage.ATK(tank);
            System.out.println(tank.getName() + " - HP: " + tank.getCurrentHP() + ", Mana: " + tank.getCurrentMana());
            System.out.println();

            // Tank โจมตี Mage
            System.out.println(tank.getName() + " attacks " + mage.getName() + " with basic attack!");
            tank.ATK(mage);
            System.out.println(mage.getName() + " - HP: " + mage.getCurrentHP() + ", Mana: " + mage.getCurrentMana());
            System.out.println();

            // Mage ใช้ความสามารถพิเศษ (Ice Magic)
            System.out.println(mage.getName() + " uses Ice Magic on " + tank.getName() + "!");
            mage.useAbility("Ice Magic", tank);
            System.out.println(tank.getName() + " - HP: " + tank.getCurrentHP() + ", Mana: " + tank.getCurrentMana());
            System.out.println();

            // Tank ใช้ความสามารถพิเศษ (Fortify Armor)
            System.out.println(tank.getName() + " uses Fortify Armor on " + mage.getName() + "!");
            tank.useAbility("Fortify Armor", mage);
            System.out.println(mage.getName() + " - HP: " + mage.getCurrentHP() + ", Mana: " + mage.getCurrentMana());
            System.out.println();

            // แสดงสเตตัสหลังจากการฟื้นฟู
            System.out.println("Current Stats after healing and regaining mana:");
            System.out.println(mage.getName() + " - HP: " + mage.getCurrentHP() + ", Mana: " + mage.getCurrentMana());
            System.out.println(tank.getName() + " - HP: " + tank.getCurrentHP() + ", Mana: " + tank.getCurrentMana());
            System.out.println();

            // แสดงการเพ่ิ่มเลเวลของอุปกรณ์เสริม
        System.out.println("Level up Necklace...");
        necklace.levelUp();
        System.out.println("Level up Bracelet...");
        necklace.levelUp();
    }
}
