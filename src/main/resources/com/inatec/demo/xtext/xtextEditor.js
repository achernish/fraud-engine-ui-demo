com_inatec_demo_xtext_XTextEditor = function() {

    /*
    var xtextEditor = xtextEditor || {};
    var serverXTextEditor = new xtextEditor.XTextEditor(this.getElement());

    // Handle changes from the server-side
    this.onStateChange = function() {
        serverXTextEditor.setValue(this.getState().value);
    };

    var self = this;
    */

    var e = this.getElement();
    e.innerHTML = "<div class='container'><div class='content'><div id='xtext-editor' data-editor-enable-save-action='true' data-editor-xtext-lang='fcrl'></div></div></div>";

    var baseUrl = "/xtext/";
    require.config({
        baseUrl: baseUrl,
        paths: {
            "jquery": "webjars/jquery/2.1.4/jquery.min",
            "ace/ext/language_tools": "webjars/ace/1.2.0/src/ext-language_tools",
            "xtext/xtext-ace": "xtext/2.9.0.rc1/xtext-ace"
        }
    });
    require(["webjars/ace/1.2.0/src/ace"], function() {
        require(["xtext/xtext-ace"], function(xtext) {
            var editor = xtext.createEditor({
                baseUrl: baseUrl,
                syntaxDefinition: "xtext-resources/generated/mode-fcrl"
            });

            editor.xtextServices.successListeners.push(function(serviceType, result) {
                if (serviceType == 'validate' && result.issues.length == 0) {
                    var rule = editor.getValue();

                    var evt = document.createEvent('HTMLEvents');
                    evt.initEvent('change', true, false);

                    var serverEditor = document.getElementById('xtext-editor');
                    serverEditor.dispatchEvent(evt);
                }
            });
        });
    });
}