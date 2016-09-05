# EMailSenderService
SpringBoot based service for e-mail sending.
The application is REST service with embedded ActiveMQ server. It takes the recipients list, images list as base64 content with keys/ids, HTML message template with placeholders for image keys/ids and embeding the content inside of message.

Tested on Mozilla Thunderbird, Google Mail and Microsoft Outlook.

## Features

The service accepts an array of a recipients object which expects:

- User e-mail as a first element
- An array of key-value map where key is a token in e-mail to be replaced by provided value.

Your e-mail message can be personalised by these variables.

Tokens are surrounded by `@` chars. Example `@url@`