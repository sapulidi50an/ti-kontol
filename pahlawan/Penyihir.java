package pahlawan;

import boss.Musuh;

public class Penyihir extends Pahlawan {
    private int mana;
    private int maxMana;

    public Penyihir(String nama) {
        super(nama, "Penyihir");
        this.attack += 5;
        this.defense -= 8;
        this.mana = 50;
        this.maxMana = 50;
    }

    @Override
    protected void initializeSkills() {
        skills.add("Bola Api");
    }

    @Override
    protected void learnNewSkill() {
        String[] availableSkills = {"Lightning Bolt", "Ice Shard", "Heal", "Meteor"};
        if (skills.size() < 5) {
            String newSkill = availableSkills[skills.size() - 1];
            skills.add(newSkill);
            maxMana += 20;
            mana = maxMana;
            System.out.println("🔮 " + nama + " mempelajari skill baru: " + newSkill);
        }
    }

    @Override
    public void useSpecialSkill(Musuh target) {
        if (skills.contains("Lightning Bolt") && mana >= 20) {
            int damage = attack * 3;
            target.takeDamage(damage);
            mana -= 20;
            System.out.println("⚡ " + nama + " menggunakan Lightning Bolt! Damage: " + damage);
        } else if (skills.contains("Bola Api") && mana >= 15) {
            int damage = attack + 15;
            target.takeDamage(damage);
            mana -= 15;
            System.out.println("🔥 " + nama + " menggunakan Bola Api! Damage: " + damage);
        } else {
            System.out.println("❌ Mana tidak cukup atau skill belum dipelajari!");
        }
    }

    @Override
    public void displayStatus() {
        super.displayStatus();
        System.out.println("Mana: " + mana + "/" + maxMana);
    }

    public void restoreMana(int amount) {
        mana = Math.min(maxMana, mana + amount);
    }
}