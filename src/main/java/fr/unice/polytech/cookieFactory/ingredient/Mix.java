package fr.unice.polytech.cookieFactory.ingredient;

public class Mix  {
    private String name;
    public Mix(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Mix{" +
                "name='" + name + '\'' +
                '}';
    }
    public String getName(){
        return name;
    }
}
