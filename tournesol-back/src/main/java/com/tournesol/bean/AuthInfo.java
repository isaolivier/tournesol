package com.tournesol.bean;

public class AuthInfo {

	String authcode;

	String email;

	String uid;

	public AuthInfo() {
	}

	public AuthInfo(String authcode, String email, String uid) {
		this.authcode = authcode;
		this.email = email;
		this.uid = uid;
	}

	/**
	 * @return the authcode
	 */
	public String getAuthcode() {
		return authcode;
	}

	/**
	 * @param authcode
	 *            the code to set
	 */
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the uid
	 */
	public String getUID() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUID(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		StringBuilder strBui = new StringBuilder();
		strBui.append(String.format("[%s]", this.getClass().getSimpleName()));
		strBui.append(String.format(" [%s.authcode]: %s -", this.getClass().getSimpleName(), this.getAuthcode() != null ? "'" + this.getAuthcode() + "'" : null));
		strBui.append(String.format(" [%s.email] %s", this.getClass().getSimpleName(), this.getEmail() != null ? "'" + this.getEmail() + "'" : null));
		strBui.append(String.format(" [%s.uid] %s", this.getClass().getSimpleName(), this.getUID() != null ? "'" + this.getUID() + "'" : null));
		return strBui.toString();
	}

}
