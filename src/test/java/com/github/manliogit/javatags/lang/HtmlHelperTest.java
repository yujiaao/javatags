package com.github.manliogit.javatags.lang;

import static com.github.manliogit.javatags.lang.HtmlHelper.a;
import static com.github.manliogit.javatags.lang.HtmlHelper.attr;
import static com.github.manliogit.javatags.lang.HtmlHelper.br;
import static com.github.manliogit.javatags.lang.HtmlHelper.div;
import static com.github.manliogit.javatags.lang.HtmlHelper.head;
import static com.github.manliogit.javatags.lang.HtmlHelper.html5;
import static com.github.manliogit.javatags.lang.HtmlHelper.link;
import static com.github.manliogit.javatags.lang.HtmlHelper.meta;
import static com.github.manliogit.javatags.lang.HtmlHelper.script;
import static com.github.manliogit.javatags.lang.HtmlHelper.text;
import static com.github.manliogit.javatags.lang.HtmlHelper.title;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.manliogit.javatags.element.Element;

public class HtmlHelperTest {
 
	@Test
	public void htmlTagRenderDocTypeAlso() throws Exception {
		assertThat(html5().render().toString(), is("<!DOCTYPE html><html></html>"));
	}
	
	@Test
	public void renderTextAsLastSibling() throws Exception {
		Element frag = div(
				   	  div(),
				   	  "text"
				   );
		
		Element frag2 = div(
				div(),
				div(),
				text("text")
			);
		
		assertThat(frag.render().toString(), is("<div><div></div>text</div>"));
		assertThat(frag2.render().toString(), is("<div><div></div><div></div>text</div>"));
	}
	
	@Test
	public void renderDivWithAttributeAndTest() throws Exception {
		assertThat(div(attr("id -> 123"), "some text").render().toString(), is("<div id='123'>some text</div>"));
	}
	
	@Test
	public void nested() throws Exception {
		Element frag = html5(
						div(attr("class -> fa fa-up"),
							div(attr("id -> 123"), "some text")
						),
						div(
							br(),
							"otherText"
						)
					);

		String expected = "<!DOCTYPE html>"
						+ "<html>"
						+ 	"<div class='fa fa-up'>"
						+ 		"<div id='123'>some text</div>"
						+ 	"</div>"
						+ 	"<div>"
						+ 		"<br/>otherText"
						+ 	"</div>"
						+ "</html>";
		
		assertThat(frag.render().toString(), is(expected));
	}
	
	@Test
	public void headBlock() throws Exception {
		Element frag = html5(attr("lang -> en"),
						head(
							meta(attr("http-equiv -> Content-Type", "content -> text/html; charset=UTF-8")),
							title("title"),
							link(attr("href -> xxx.css", "rel -> stylesheet"))
						)
					);
		
		String expected = "<!DOCTYPE html>"+
							"<html lang='en'>" +
								"<head>"+
								    "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>" +
								    "<title>title</title>" +
								    "<link href='xxx.css' rel='stylesheet'/>"+
								"</head>" +
						    "</html>";
		
		assertThat(frag.render().toString(), is(expected));
	}
	
	@Test
	public void renderJsOnAttribute() throws Exception {
		Element frag = div(
						 a(attr("href -> xxx", "onclick -> alert(\"yay!\")"), "xxx")
					   );
		
		assertThat(frag.render().toString(), is("<div><a href='xxx' onclick='alert(\"yay!\")'>xxx</a></div>" ));
	}
	
	@Test
	public void renderJsWithinScript() throws Exception {
		Element frag = script(attr("type -> text/javascript"),
							"function xxx{" +
								"alert('yay!');" +
							"}"
					   );
		
		assertThat(frag.render().toString(), is("<script type='text/javascript'>function xxx{alert('yay!');}</script>" ));
	}
}
