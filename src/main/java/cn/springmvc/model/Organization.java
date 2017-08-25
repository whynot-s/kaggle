package cn.springmvc.model;

/**
 * Created by YLT on 2017/8/24.
 */
public class Organization {
    private int organizationId;
    private String organizationName;
    private int organizationMemberId;
    private String organizationMemberName;

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getOrganizationMemberId() {
        return organizationMemberId;
    }

    public void setOrganizationMemberId(int organizationMemberId) {
        this.organizationMemberId = organizationMemberId;
    }

    public String getOrganizationMemberName() {
        return organizationMemberName;
    }

    public void setOrganizationMemberName(String organizationMemberName) {
        this.organizationMemberName = organizationMemberName;
    }
}
