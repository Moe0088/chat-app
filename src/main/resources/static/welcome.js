const usernameTextbox = document.getElementById("name");
const nameError = document.getElementById("nameError");
const welcomeForm = document.getElementById("welcomeForm");

usernameTextbox.addEventListener("blur", () => {
    const typedName = usernameTextbox.value.trim();
    nameError.textContent = "";
    if (!typedName) {
        nameError.textContent = "Name cannot be Empty";
        usernameTextbox.focus();
        usernameTextbox.select()
        return;
    }
    fetch(`/users/check?name=${encodeURIComponent(typedName)}`)
        .then(res => res.json())
        .then(isTaken => {
            if (isTaken) {
                nameError.textContent = "That name is already taken";
                usernameTextbox.focus();
                usernameTextbox.select();
            }
        })
        .catch(() => {

        });

});
welcomeForm.addEventListener("submit", (e) => {
    const typedName = usernameTextbox.value.trim();
    nameError.textContent = "";
    if (!typedName) {
        nameError.textContent = "Name cannot be Empty";
        usernameTextbox.focus();
        usernameTextbox.select()
        return;
    }
    if (nameError.textContent === "That name is already taken") {
        e.preventDefault();
        usernameTextbox.focus();
        usernameTextbox.select();
    }
});

