package org.jboss.errai.security.shared.api.identity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;

/**
 * A user.
 * 
 * @author edewit@redhat.com
 */
@Portable
@Bindable
public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  private String loginName;
  private String lastName;
  private String firstName;
  private String email;
  private Set<Role> roles;

  public User(String loginName, String firstName, String lastName, String email, Set<Role> roles) {
    this.loginName = loginName;
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.roles = roles;
  }

  public User(String loginName, String firstName, String lastName, String email) {
    this(loginName, firstName, lastName, email, new HashSet<Role>(0));
  }

  public User(String loginName, String firstName, String lastName) {
    this(loginName, firstName, lastName, "");
  }

  public User(String loginName) {
    this(loginName, "", "");
  }

  public User() {
    this("");
  }

  public void setLoginName(String loginName) {
    this.loginName = filterNull(loginName);
  }

  public String getLoginName() {
    return loginName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = filterNull(lastName);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = filterNull(firstName);
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = filterNull(email);
  }

  @Override
  public int hashCode() {
    return loginName != null ? loginName.hashCode() : 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof User))
      return false;

    User user = (User) o;

    return email.equals(user.email)
            && lastName.equals(user.lastName)
            && loginName.equals(user.loginName)
            && firstName.equals(user.firstName)
            && roles.equals(user.roles);
  }

  private String filterNull(final String value) {
    return (value == null) ? "" : value;
  }

  @Override
  public String toString() {
    return "User{" + "loginName='" + loginName + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
            + '\'' + ", email='" + email + '\'' + ", roles=" + roles.toString() + ' ' + '}';
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    if (roles == null) {
      this.roles = new HashSet<Role>(0);
    }
    else {
      this.roles = roles;
    }
  }
}
