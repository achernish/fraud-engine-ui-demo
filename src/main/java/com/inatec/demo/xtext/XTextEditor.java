package com.inatec.demo.xtext;


import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.data.Property;
import com.vaadin.ui.AbstractJavaScriptComponent;
import com.vaadin.ui.Notification;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Anatoly Chernysh
 */
@StyleSheet(value = {"http://localhost:8080/xtext/xtext/2.9.0.rc1/xtext-ace.css", "xtextEditor.css"})
@JavaScript(value = {"http://localhost:8080/xtext/webjars/requirejs/2.1.20/require.min.js", "xtextEditor.js"})
public class XTextEditor extends AbstractJavaScriptComponent {

    public XTextEditor() {
    }

    public interface ValueChangeListener extends Serializable {
        void valueChange();
    }

    ArrayList<ValueChangeListener> listeners = new ArrayList<ValueChangeListener>();

    public void addValueChangeListener(ValueChangeListener listener) {
        listeners.add(listener);
    }

    public void setValue(String value) {
        getState().value = value;
    }

    public String getValue() {
        return getState().value;
    }

    @Override
    protected XTextEditorState getState() {
        return (XTextEditorState) super.getState();
    }
}