// Source code is decompiled from a .class file using FernFlower decompiler.
package pahlawan;
import boss.Musuh;
import java.util.ArrayList;
import java.util.List;

public abstract class Pahlawan {
   protected String nama;
   protected int level;
   protected int hp;
   protected int maxHp;
   protected int attack;
   protected int defense;
   protected int exp;
   protected int expToNext;
   protected List<String> skills;
   protected String kelas;

   public Pahlawan(String var1, String var2) {
      this.nama = var1;
      this.kelas = var2;
      this.level = 1;
      this.hp = 100;
      this.maxHp = 100;
      this.attack = 20;
      this.defense = 10;
      this.exp = 0;
      this.expToNext = 100;
      this.skills = new ArrayList<String>();
      this.initializeSkills();
   }

   protected abstract void initializeSkills();

   public abstract void useSpecialSkill(boss.Musuh var1);

   public void levelUp() {
      ++this.level;
      this.maxHp += 20;
      this.hp = this.maxHp;
      this.attack += 5;
      this.defense += 3;
      this.exp = 0;
      this.expToNext = this.level * 100;
      if (this.level % 2 == 0 && this.skills.size() < 5) {
         this.learnNewSkill();
      }

      System.out.println("\ud83c\udf89 " + this.nama + " naik ke level " + this.level + "!");
      System.out.println("HP: " + this.maxHp + " | ATK: " + this.attack + " | DEF: " + this.defense);
   }

   protected abstract void learnNewSkill();

   public void gainExp(int var1) {
      this.exp += var1;
      System.out.println(this.nama + " mendapat " + var1 + " EXP!");

      while(this.exp >= this.expToNext) {
         this.levelUp();
      }

   }

   public void attack(Musuh var1) {
      int var2 = Math.max(1, this.attack - var1.getDefense());
      var1.takeDamage(var2);
      String var10001 = this.nama;
      System.out.println(var10001 + " menyerang " + var1.getNama() + " sebesar " + var2 + " damage!");
   }

   public void takeDamage(int var1) {
      this.hp -= var1;
      if (this.hp < 0) {
         this.hp = 0;
      }

   }

   public void heal(int var1) {
      this.hp = Math.min(this.maxHp, this.hp + var1);
   }

   public boolean isAlive() {
      return this.hp > 0;
   }

   public void displayStatus() {
      System.out.println("=== " + this.nama + " (" + this.kelas + ") ===");
      System.out.println("Level: " + this.level + " | HP: " + this.hp + "/" + this.maxHp);
      System.out.println("ATK: " + this.attack + " | DEF: " + this.defense);
      System.out.println("EXP: " + this.exp + "/" + this.expToNext);
      System.out.println("Skills: " + String.valueOf(this.skills));
   }

   public String getNama() {
      return this.nama;
   }

   public int getLevel() {
      return this.level;
   }

   public int getHp() {
      return this.hp;
   }

   public int getMaxHp() {
      return this.maxHp;
   }

   public int getAttack() {
      return this.attack;
   }

   public int getDefense() {
      return this.defense;
   }

   public List<String> getSkills() {
      return this.skills;
   }

   public String getKelas() {
      return this.kelas;
   }
}
