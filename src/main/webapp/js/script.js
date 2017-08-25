/**
 * 
 */
$(function () {
    $("#tree_models").jstree({
        
    });
    $("#tree_models").bind("changed.jstree",
    function (e, data) {
        //alert("Checked: " + data.node.id);
        //alert("Parent: " + data.node.parent); 
        //alert(JSON.stringify(data));
    });
});

$(function () {
    $("#tree_datamap").jstree({
        "checkbox": {
            "keep_selected_style": false
        },
            "plugins": ["checkbox"]
    });
    $("#tree_datamap").bind("changed.jstree",
    function (e, data) {
        //alert("Checked: " + data.node.id);
        //alert("Parent: " + data.node.parent); 
        //alert(JSON.stringify(data));
    });
});

