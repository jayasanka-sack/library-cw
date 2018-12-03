    package models;

    import io.ebean.Model;

    import javax.persistence.Column;
    import javax.persistence.Entity;
    import javax.persistence.Id;
    import javax.persistence.Table;

    @Entity
    @Table(name = "reader")
    public class ReaderModel extends Model {

        @Id
        @Column(name = "id")
        private int id;

        @Column(name = "name")
        private String name;

        @Column(name = "mobile")
        private String mobile;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Column(name = "email")
        private String email;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
