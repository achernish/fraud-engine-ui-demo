package com.inatec.demo.xtext;


import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.data.Property;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Notification;
import elemental.json.JsonArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Anatoly Chernysh
 */
@StyleSheet(value = {"http://localhost:8080/xtext/xtext/2.9.0.rc1/xtext-ace.css", "xtextEditor.css"})
@JavaScript(value = {"http://localhost:8080/xtext/webjars/requirejs/2.1.20/require.min.js", "xtextEditor.js"})
public class XTextEditor extends AbstractJavaScriptComponent {

    public XTextEditor() {
        addFunction("setValue", new JavaScriptFunction() {
            @Override
            public void call(JsonArray arguments) {
                String value = arguments.getString(0);
                getState().value = value;
            }
        });
    }

    public String getValue() {
        return getState().value;
    }

    public void setValue(String value) {
        getState().value = value;
    }

    @Override
    protected XTextEditorState getState() {
        return (XTextEditorState) super.getState();
    }
}