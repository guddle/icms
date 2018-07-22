package com.ictframe.data.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 
 * @author jerry
 * @Date Jul 16, 2018
 */
@Entity
@Table(name = "t_permission")
public class Permission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5158262506750685721L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;//主键

	private String name;//名称
	
	@Column(columnDefinition = "enum('menu','button')")
	private String resourceType;//资源类型，[menu|button]
	
	private String url; //资源路径
	
	private Long parentId; //父节点编号
	
	private Boolean available = Boolean.FALSE;
	
	private String permission;// 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
	@ManyToMany
	@JoinTable(name = "t_role_permissions", joinColumns = {@JoinColumn(name = "permission_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private List<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	
}
