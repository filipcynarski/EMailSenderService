# EMailSenderService
SpringBoot based service for e-mail sending.
The application is REST service with embeded ActiveMQ server. It takes the recipients list, images list as base64 content with keys/ids, HTML message template with placeholders for image keys/ids and embeding the content inside of message.

Tested on Mozilla Thunderbird, Google Mail and Microsoft Outlook.
