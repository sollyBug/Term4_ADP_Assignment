package za.ac.cput.term4_adp_assignment.authManager;

public class Course {
    private String courseId;     // <-- add this
    private String name;
    private String type;
    private int credits;
    private String description;

    public Course(String courseId, String name, String type, int credits, String description) {
        this.courseId = courseId;
        this.name = name;
        this.type = type;
        this.credits = credits;
        this.description = description;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCredits() {
        return credits;
    }

    public String getDescription() {
        return description;
    }
}
