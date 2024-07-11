package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * The TeamMember class represents a member of a basketball team.
 * It encapsulates common attributes such as name, role, and age.
 * The class provides methods to access these attributes. This class
 * serves as a base for other classes in the 'model' package that represent
 * specific roles within the team.
 */
public abstract class TeamMember implements Writable {
    protected String name;
    protected int age;

    public TeamMember(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }


    public abstract JSONObject toJson();
}
