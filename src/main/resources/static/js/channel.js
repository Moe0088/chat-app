// Grab the container that holds all messages, so we can replace its contents during polling
let messageList = document.getElementById("messageList");
const form = document.getElementById("messageForm");
// Grab the textarea where the user types their message
const input = document.getElementById("textArea");

let BackButton = document.getElementById("backBtn")

BackButton.addEventListener('click', () => {
    window.location.href = '/';
});


input.addEventListener('keydown', e => {
    // if the user pressed ENTER without holding SHIFT
    if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();               // don’t insert a newline
        form.dispatchEvent(new Event('submit', {cancelable: true}));
        // this will re-use your form.submit handler (the one that does the AJAX fetch)
    }
});
// Listen for the form’s “submit” event instead of the button’s click.
// This also catches pressing “Enter” inside the textarea.
form.addEventListener('submit', (e) => {
    // Stop the browser’s default behavior (sending a POST and reloading the page)
    e.preventDefault();

    // Read the text the user typed and remove extra spaces at the start/end
    const content = input.value.trim();
    console.log(content);
    // If there’s nothing left after trimming, do nothing (don’t send blank messages)
    if (!content) {

        return;
    }

    // Send the message to the server in the background using fetch()
    // The URL includes the current channelId so the server knows where to store it.
    fetch(`/channel/${channelId}`, {
        // HTTP POST to submit data
        method: 'POST', headers: {
            // Tell server we’re sending form-encoded data
            "Content-Type": "application/x-www-form-urlencoded"
        }, // Package our message as “content=…”
        body: new URLSearchParams({content})
    })
        .then(response => {
            // If the server returned an error status, just stop here
            if (!response.ok) {
                return;
            }
            // On success, clear the textarea so the user can type their next message
            input.value = '';
        })
        .catch(err => // If the network call itself failed, log it for debugging
            console.error("Network error:", err));


});

function fetchMessages() {
    console.log("Polling for messages...");
    fetch(`/channel/${channelId}/messages`)
        .then(response => response.json())
        .then(messages => {
            // Clear the old messages
            messageList.innerHTML = "";

            // Reinsert the new messages
            messages.forEach(message => {
                const p = document.createElement("p");
                const username = message.user?.name || "Unknown";
                p.innerHTML = `<strong>${username}</strong>: ${message.content}`;
                messageList.appendChild(p);
            });
        })
        .catch(error => {
            console.error("Failed to fetch messages:", error);
        });
}

// Run once
fetchMessages();

// Repeat every 0.5 seconds
setInterval(fetchMessages, 500);
