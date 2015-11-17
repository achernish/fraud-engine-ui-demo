package com.inatec.demo.xtext;


import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.AbstractJavaScriptComponent;

/**
 * @author Anatoly Chernysh
 */
@StyleSheet(value = {"http://localhost:8080/xtext/xtext/2.9.0.rc1/xtext-ace.css", "xtextEditor.css"})
@JavaScript(value = {"http://localhost:8080/xtext/webjars/requirejs/2.1.20/require.min.js", "xtextEditor.js"})
public class XTextEditor extends AbstractJavaScriptComponent {

    public XTextEditor() {
    }
}