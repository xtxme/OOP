public class Lab06 {
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
         * @return: current mana of the character.
         * @effects: none
         */

        public int getCurrentMana() {
            return currentMana;
        }

        /**
         * @param: none
         * @return: current HP of the character.
         * @effects: none
         */
        public int getCurrentHP() {
            return currentHP;
        }

        /**
         * @param: none
         * @return name of the character.
         * @effects: none
         */
        public String getName() {
            return name;
        }
        /**
         * @param: none
         * @return: none
         * @effects: - increase level of the character by 1.
         *           - recalulate the basic stats.
         *           - print a message in the console to tell that the character has been upgraded.
         */
        public void levelUp() {
            this.level++;
            recalStats();
            System.out.println(name + " leveled up to level " + level + "!");
        }

        /**
         * @param: none
         * @return: none
         * @effects: - recalculates all stats based on the current level.
         *           - print a message in the console to tell that character has been recalculated stats.
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

        /**
         * @param: none
         * @return: none
         * @effects: - ปรับความเร็วตามผลของอุปกรณ์เสริมที่ส่วมใส่
         *           - พิมพ์ข้อความในคอนโซลเพื่อบอกให้ทราบว่าตัวละครถูกปรับความเร็วเป็นเท่าไหร่
         */

        public void adjustSpeed() {
            if (necklace != null) {
                SPD += necklace.calEffect() / 10; // เพิ่มสปีดตามผลของสร้อยคอ
            }
            if (bracelet != null) {
                SPD -= bracelet.calEffect() / 10; // ลดสปีดตามผลของกำไล
            }
            System.out.println(name + "'s speed adjusted to " + SPD);
        }

        /**
         * @param accessory to equip
         * @return: none
         * @effects: - ติดตั้งอุปกรณ์เสริมที่รับเข้ามาให้กับตัวละคร
         *           - พิมพ์ข้อความในคอนโซลเพื่อบอกให้ทราบว่าตัวละครส่วมใสุ่ปกรณ์เสริมอะไรอยู่
         *           - ปรับค่าความเร็วของตัวละคร
         */

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

        /**
         * @param target the character to be attacked.
         * @return: none
         * @effects: - ลดค่า HP ของเป้าหมายตามความเสียหายที่คำนวณออกมา
         *           - พิมพ์ข้อความในคอนโซลเพื่อบอกให้ทราบว่าตัวละครถูกโจมตีด้วยดาเมจเท่าไหร่
         * */

        public void ATK(Character target) {
            int damage = Math.max(0, ATKPower - target.DEF); // ดาเมจไม่ต่ำกว่า 0
            target.currentHP = Math.max(0, target.currentHP - damage); // HP ไม่ต่ำกว่า 0
            System.out.println(name + " attacks " + target.name + " dealing " + damage + " damage");
        }

        /**
         * @param ability ที่ใช้ได้
         * @param target เป้าหมายที่จะใช้ความสามารถด้วย
         * @return: none
         * @effects: - สร้างความเสียหายให้กับเป้าหมายและลดมานาปัจจุบันของตัวละครที่โจมตี
         *           - พิมพ์ข้อความในคอนโซลเพื่อบอกให้ทราบว่าตัวละครใช้ความสามารถอะไรโจมตีและสร้างความเสียหายเท่าไหร่
         *             หรือพิมพ์ข้อความในคอนโซลเพื่อบอกว่ามานาไม่พอใช้
         */
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

        /**
         * @param: none
         * @return: ประเภทธาตุเวทย์มนตร์ของตัวละคร
         * @effects: none
         */
        public String getElementalMagic() {
            return elementalMagic;
        }

        /**
         * @param elementalMagic ประเภทธาตุเวทย์มนตร์ใหม่ที่เตรียมกำหนดให้กับตัวละคร
         * @return: none
         * @effects: - อัปเดตเปลี่ยนเวทย์มนตร์ธาตุของตัวละคร
         *           - พิมพ์ข้อความในคอนโซลเเพื่อบอกการเปลี่ยนแปลงว่าเปลี่ยนเป็นธาตุอะไร
         */

        public void setElementalMagic(String elementalMagic) {
            this.elementalMagic = elementalMagic;
            System.out.println(name + " changed elemental magic to " + elementalMagic + ".");
        }

        /**
         * @param spellName ชื่อของเวทย์ที่จะร่าย
         * @param target เป้าหมายที่จะใช้ความสามารถด้วย
         * @return: none
         * @effects: - ร่ายเวทย์ใส่เป้าหมายและลดมานา
         *           - พิมพ์ข้อความในคอนโซลเพื่อบอกให้ทราบว่าตัวละครใช้เวทย์โจมตีและสร้างความเสียหายเท่าไหร่
         *             หรือพิมพ์ข้อความในคอนโซลเพื่อบอกว่ามานาไม่พอใช้
         */

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

        /**
         *
         * @param spellName ชื่อของเวทย์ที่จะร่าย
         * @return: ค่ามานาที่ต้องเสียของคาถา
         * @effect: none
         */

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

        /**
         * @param spellName ชื่อของเวทย์ที่ต้องการเพิ่มพลังโจมตี
         * @return: none
         * @effects: - เพิ่มพลังโจมตีของตัวละครที่ร่าย
         *           - พิมพ์ข้อความในคอนโซลเพื่อบอกให้ทราบว่าตัวละครร่ายมนต์เพื่อเพิ่มพลังโจมตีเป็นเท่าไหร่
         */

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

        /**
         * @param amount ค่าที่อยากจะเพิ่มการป้องกัน
         * @return: none
         * @effects: - เพิ่มค่าการป้องกันของตัวละคร
         *           - พิมพ์ข้อความในคอนโซลเพือเเจ้งว่าตัวละครมีการเพิ่มค่าการป้องกันเป็นเท่าไหร่
         */

        @Override
        public void fortifyArmor(int amount) {
            DEF += amount;
            System.out.println(name + " fortifies armor by " + amount + ". Current DEF: " + DEF);
        }

        /**
         * @param ally พันธมิตรที่จะดูดซับความเสียหายให้
         * @return: none
         * @effects: - พิมพ์ข้อความลงในคอนโซลเพื่อแจ้งว่าตัวละครกำลังดูดซับความเสียหายแทนพันธมิตร
         *           - เปลี่ยนแปลงเพิ่มค่าการป้องกันของพันธมิตร ด้วยค่าครึ่งหนึ่งของค่าการป้องกันของตัวละคร
         */

        @Override
        public void absorbDamage(Character ally) {
            System.out.println(name + " absorbs damage for " + ally.name);
            ally.DEF += this.DEF / 2;
        }

        /**
         * @param amount จำนวนค่าที่ต้องการซ่อมแซมการป้องกัน
         * @return: none
         * @effect: - ค่าการป้องกันจะเพิ่มขึ้นตามค่าที่เราใส่เข้าไป เป็นการซ่อมแซมค่าการป้องกันของตัวละคร
         *          - พิมพ์ข้อความลงในคอนโซลเพื่อแจ้งว่าตัวละครซ่อมแซมค่าการป้องกันเป็นเท่าไหร่
         */

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

        /**
         * @param: none
         * @return: ประเภทของอุปกรณ์เสริม
         * @effects: none
         */

        public String getAccessoryClass() {
            return accessoryClass;
        }

        /**
         * @param: none
         * @return: ระดับปัจจุบันของอุปกรณ์เสริม
         * @effects: none
         */
        public int getLevel() {
            return level;
        }

        /**
         * @param: none
         * @return: ค่าการคำนวณของอุปกรณ์เสริม
         * @effects: none
         */
        public int calEffect() {
            return calEffect;
        }

        /**
         * @param: none
         * @return: none
         * @effects: - เพิ่มระดับอุปกรณ์เสริม
         *           - พิมพ์ข้อความลงในคอนโซลเพื่อแจ้งว่าอุปกรณ์เสริมอยู่ระดับที่เท่าไหร่
         */
        public void levelUp() {
            level++;
            System.out.println(accessoryClass + " leveled up to level " + level + "!");
        }

        /**
         * @param: none
         * @return: ค่าการลดความเร็วที่คำนวณได้ของอุปกรณ์เสริม
         * @effects: - ค่าที่ลดลงมาจากการคูณค่าที่ตั้งไว้กับระดับของอุปกรณ์เสริม
         *
         */
        public int calSpeedDecrease() {
            return baseSpeedForDecrease * level;
        }

        /**
         * @param: none
         * @return: ค่าเพิ่มความเร็วที่คำนวณได้ของอุปกรณ์เสริม
         * @effects: - ค่าที่เพิ่มขึ้นมาจากการคูณค่าที่ตั้งไว้กับระดับของอุปกรณ์เสริม
         */
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

        /**
         * @param: none
         * @return: none
         * @effect: - เพิ่มค่าการโจมตีสำหรับ damageBonus ของสร้อยคอโดยมาจาก 5 คูณกับระดับ level
         *          - อัปเดตค่า calEffect ให้เท่ากับ damageBonus
         */

        @Override
        protected void adjustStatsForLevelUp() {
            // เพิ่มค่าพลังโจมตีเมื่ออุปกรณ์เลเวลสูงขึ้น
            this.damageBonus += 5 * level;
            this.calEffect = damageBonus;
        }

        /**
         * @param: none
         * @return: damage bonus ที่ได้รับจากสร้อยคอ
         * @effects: none
         */

        @Override
        public int calEffect() {
            return damageBonus;  // ค่าพลังโจมตีที่เพิ่มขึ้นจากสร้อยคอ
        }

        /**
         * @param: none
         * @return: none
         * @effects: - เพิ่มระดับของสร้อยคอ 1 ระดับ
         *           - ปรับค่าสเตตัส
         *           - พิมพ์ข้อความลงในคอนโซลเพื่อแจ้งว่าสร้อยคอถูกเพิ่มระดับ
         */

        public void levelUp() {
            level++;  // เพิ่มระดับของสร้อยคอ
            adjustStatsForLevelUp();  // ปรับปรุงสเตตัสหลังจากเพิ่มระดับ
            System.out.println("Necklace leveled up to level " + level + "!");
        }

        /**
         * @param: none
         * @return: คืนค่า damage bonus ปัจจุบันที่ได้จากสร้อยคอ
         * @effect: none
         */

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

        /**
         * @param: none
         * @return: none
         * @effect: - เพิ่มค่าการป้องกันสำหรับ defenseBonus ของกำไลโดยมาจาก 3 คูณกับระดับ level
         *          - อัปเดตค่า calEffect ให้เท่ากับ defenseBonus
         */


        @Override
        protected void adjustStatsForLevelUp() {
            // เพิ่มค่าการป้องกันเมื่ออุปกรณ์เลเวลสูงขึ้น
            this.defenseBonus += 3 * level;
            this.calEffect = defenseBonus;
        }

        /**
         * @param: none
         * @return: ค่า defenseBonus ที่ได้รับจากกำไล
         * @effects: none
         */

        @Override
        public int calEffect() {
            return defenseBonus;  // ค่าการป้องกันที่เพิ่มขึ้นจากกำไล
        }

        /**
         * @param: none
         * @return: none
         * @effects: - เพิ่มระดับของกำไล 1 ระดับ
         *           - ปรับค่าสเตตัส
         *           - พิมพ์ข้อความลงในคอนโซลเพื่อแจ้งว่ากำไลถูกเพิ่มระดับ
         */

        public void levelUp() {
            level++;  // เพิ่มระดับของกำไล
            adjustStatsForLevelUp();  // ปรับปรุงสเตตัสหลังจากเพิ่มระดับ
            System.out.println("Bracelet leveled up to level " + level + "!");
        }

        /**
         * @param: none
         * @return: ค่า defenseBonus ปัจจุบันที่ได้จากกำไล
         * @effects: none
         */

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
