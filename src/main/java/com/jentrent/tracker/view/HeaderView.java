package com.jentrent.tracker.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class HeaderView extends BaseView implements Serializable {

	@PostConstruct
	public void init() {

		super.init();
	}

	public String getCurrentPage() {

		String id = FacesContext.getCurrentInstance().getViewRoot().getViewId();

		return id.substring(1, id.length() - 6);

	}

}
