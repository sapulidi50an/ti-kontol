package pahlawan;
import boss.Musuh;

public class Pemanah extends Pahlawan {
    public Pemanah(String nama) {
        super(nama, "Pemanah");
        this.attack += 10;
        this.defense -= 2;
    }

    @Override
    protected void initializeSkills() {
        skills.add("Panah Cepat");
    }

    @Override
    protected void learnNewSkill() {
        String[] availableSkills = {"Panah Beracun", "Hujan Panah", "Panah Api", "Serangan Akurat"};
        if (skills.size() < 5) {
            String newSkill = availableSkills[skills.size() - 1];
            skills.add(newSkill);
            System.out.println("🏹 " + nama + " mempelajari skill baru: " + newSkill);
        }
    }

    @Override
    public void useSpecialSkill(Musuh target) {
        if (skills.contains("Hujan Panah")) {
            int damage = attack + 20;
            target.takeDamage(damage);
            System.out.println("🌧️ " + nama + " menggunakan Hujan Panah! Damage: " + damage);
        } else {
            int damage = attack + 8;
            target.takeDamage(damage);
            System.out.println("🏹 " + nama + " menggunakan Panah Cepat! Damage: " + damage);
        }
    }
}