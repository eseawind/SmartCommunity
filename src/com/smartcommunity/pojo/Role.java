package com.smartcommunity.pojo;

public class Role {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.id
     *
     * @mbggenerated Tue Jan 20 16:35:08 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.rolename
     *
     * @mbggenerated Tue Jan 20 16:35:08 CST 2015
     */
    private String rolename;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column role.description
     *
     * @mbggenerated Tue Jan 20 16:35:08 CST 2015
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.id
     *
     * @return the value of role.id
     *
     * @mbggenerated Tue Jan 20 16:35:08 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.id
     *
     * @param id the value for role.id
     *
     * @mbggenerated Tue Jan 20 16:35:08 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.rolename
     *
     * @return the value of role.rolename
     *
     * @mbggenerated Tue Jan 20 16:35:08 CST 2015
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.rolename
     *
     * @param rolename the value for role.rolename
     *
     * @mbggenerated Tue Jan 20 16:35:08 CST 2015
     */
    public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column role.description
     *
     * @return the value of role.description
     *
     * @mbggenerated Tue Jan 20 16:35:08 CST 2015
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column role.description
     *
     * @param description the value for role.description
     *
     * @mbggenerated Tue Jan 20 16:35:08 CST 2015
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}