document.addEventListener("DOMContentLoaded", function () {
    const userForm = document.getElementById("peopleform");

    // Create a message element if the page doesn't have one
    let messageEl = document.getElementById("message");
    if (!messageEl) {
        messageEl = document.createElement("p");
        messageEl.id = "message";
        userForm.insertAdjacentElement("afterend", messageEl);
    }

    userForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        const userData = {
            name: document.getElementById("name").value.trim(),
            email: document.getElementById("email").value.trim(),
        };

        try {
            const response = await fetch("/api/v1/People", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(userData),
            });

            const responseText = await response.text();

            if (!response.ok) {
                throw new Error(`Server error ${response.status}: ${responseText}`);
            }

            // Some APIs return an empty body on success
            const result = responseText ? JSON.parse(responseText) : {};

            messageEl.innerText = "User saved successfully! : " + (result.name ?? userData.name);
            userForm.reset();
        } catch (error) {
            console.error("Error:", error);
            messageEl.innerText = error.message;
        }
    });
});