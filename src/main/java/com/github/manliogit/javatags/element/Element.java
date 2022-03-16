package com.github.manliogit.javatags.element;

public interface Element {

	StringBuilder render();
	Element add(Element element);
}
