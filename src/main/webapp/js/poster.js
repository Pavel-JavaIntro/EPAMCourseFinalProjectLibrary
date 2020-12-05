function postTo(command) {
    var form = document.createElement("form");
    form.setAttribute("method", "post");
    form.setAttribute("action", "library");

    var hiddenField = document.createElement("input");
    hiddenField.setAttribute("type", "hidden");
    hiddenField.setAttribute("name", "command");
    hiddenField.setAttribute("value", command);

    form.appendChild(hiddenField);

    document.body.appendChild(form);
    form.submit();
}