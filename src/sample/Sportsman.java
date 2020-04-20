package sample;

public class Sportsman {
    private String fullName;
    private String structure;
    private String position;
    private int title;
    private String kindOfSport;
    private String category;

    void setStructure(int structure){
        switch (structure){
            case 0:{
                this.structure = "основной";
                break;
            }
            case 1:{
                this.structure = "запасной";
                break;
            }
            default:{
                this.structure = "n/a";
            }
        }
    }

    void setCategory(int category){
        switch (category){
            case 0:{
                this.category = "мастер спорта";
                break;
            }
            case 1:{
                this.category = "кмс";
                break;
            }
            case 2:{
                this.category = "3-й разряд";
                break;
            }
            case 3:{
                this.category = "2-й разряд";
                break;
            }
            default:{
                this.category = "1-й юношеский";
            }
        }
    }

    void setFullName(String fullName){
        this.fullName = fullName;
    }

    void setPosition(String position){
        this.position = position;
    }

    void setTitle(int title){
        this.title = title;
    }

    void setKindOfSport(String kindOfSport){
        this.kindOfSport = kindOfSport;
    }


    Sportsman(String fullName, int structure, String position, int title, String kindOfSport, int category){
        setFullName(fullName);
        setStructure(structure);
        setPosition(position);
        setTitle(title);
        setKindOfSport(kindOfSport);
        setCategory(category);

    }

    public int getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getFullName() {
        return fullName;
    }

    public String getKindOfSport() {
        return kindOfSport;
    }

    public String getPosition() {
        return position;
    }

    public String getStructure() {
        return structure;
    }
}
