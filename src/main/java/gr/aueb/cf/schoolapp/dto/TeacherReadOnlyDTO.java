package gr.aueb.cf.schoolapp.dto;

public class TeacherReadOnlyDTO extends BaseTeacherDTO {
    private Integer id;

    public TeacherReadOnlyDTO() {}

    public TeacherReadOnlyDTO(Integer id, String firstName, String lastName) {
        super(firstName, lastName);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
