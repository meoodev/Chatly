## Overview

**Chatly** is a modern Minecraft plugin designed to improve player communication and interaction.  
It adds global and local chat, player mentions, private messages, chat bubbles above players’ heads, and simple interaction mechanics.

The plugin is modular, highly configurable, and suitable for both small SMP servers and large communities.

---

![Chat](https://github.com/Nek0rise/Socially/blob/main/media/chat_label.png?raw=true)

Socially provides an advanced and flexible chat system:

- **Global chat** for server-wide communication
- **Local chat** with a configurable radius (N blocks)
- **Player mentions**  
  Typing a player’s nickname in chat highlights it and plays a sound for the mentioned player
- **Private messages** 

The recipient hears a sound when a message is received
- **Simple bad word filter**  
Fully configurable and optional

![Chat demo](https://github.com/Nek0rise/Socially/blob/main/media/chat_screen.png?raw=true)
![DM demo](https://github.com/Nek0rise/Socially/blob/main/media/dm_screen.png?raw=true)

---

![Chat Bubbles](https://github.com/Nek0rise/Socially/blob/main/media/chatbubbles_label.png?raw=true)

Chat bubbles display messages above players’ heads:

- Appear when a player sends any chat message
- Implemented using **TextDisplay**, allowing deep customization
- Not visible through blocks (line-of-sight based)
- Fully configurable appearance and behavior

This feature improves immersion and makes nearby conversations easier to follow.

![Chatbubble demo](https://github.com/Nek0rise/Socially/blob/main/media/chat_bubble.png?raw=true)

---

![Handshakes](https://github.com/Nek0rise/Socially/blob/main/media/handshakes_label.png?raw=true)

Handshakes are a simple interaction feature:

- Player **A** right-clicks player **B** -> Player **A** sees player **B**’s nickname in the **ActionBar**

This is especially useful on SMP servers where player nicknames are hidden or difficult to recognize.

![Handshake demo](https://github.com/Nek0rise/Socially/blob/main/media/handshakes.png?raw=true)
---

![MiniMessage](https://github.com/Nek0rise/Socially/blob/main/media/minimessage_label.png?raw=true)

This plugin fully relies on **MiniMessage** for text formatting.

If you encounter any formatting issues, please create an issue on the GitHub project page and describe the problem in detail.

---

## Additional Features

- **PlaceholderAPI** support
- Modular configuration - enable only the features you need

If this project gains popularity, more features and improvements will be added soon.

---

## TODO
- 
- Add legacy format codes support (`&#RRGGBB`)
- Improve PlaceholderAPI parsing
- Add more configuration options for chat bubbles
- Add customizable placeholders in chat `:pos:` `:hp:` and more

---

## Permissions

- `chatly.msg` — Use `/msg`, `/w`, `/m`
- `chatly.reload` — Use `/chatly reload`
- `chatly.handshake` — Use handshakes
- `chatly.color` — Send colored chat messages
- `chatly.badwordbypass` — Bypass bad word filter
- `chatly.ignore` - Ignore player messages

