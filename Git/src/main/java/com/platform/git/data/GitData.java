package com.platform.git.data;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class GitData {

private String repoName;

private boolean type;
public String getRepoName() {
	return repoName;
}
public void setRepoName(String repoName) {
	this.repoName = repoName;
}
public boolean getType() {
	return type;
}
public void setType(boolean type) {
	this.type = type;
}

}
