# Feedbrico - a small feedback application

## 🏙️ Preview

### Feedback form

![Feedback form](./documentation/app_preview/feedback_submission_form.jpg)

### Feedback form submission error

![Feedback submission error](./documentation/app_preview/feedback_submission_form_error.jpg)

### Feedback form submission success popup

![Feedback submission success](./documentation/app_preview/feedback_submission_form_success_popup.jpg)

---

## 🚀 How to run the app in the development mode?

Front-end:
```shell
npm install # Only once
npm run dev
```

Back-end:
```shell
mvn clean install package # Only once
SPRING_PROFILES_ACTIVE=dev mvn spring-boot:run
```

```powershell
$env:SPRING_PROFILES_ACTIVE = 'dev'; mvn spring-boot:run
```

## 🧪 How to run tests?

```shell
mvn test
```

## 🧩 Implementation considerations/notes

* To implement professional-grade CQRS I can use an Axon framework, but it is designed for large-scale applications, so I picked a built-in Spring application event listener and publisher.
* To sync read storage with writing storage, I can use an Inbox/Outbox pattern, but it's more time-consuming, so I resigned from it. I passed data from a model as byte array payload.
* Ideally would have something like sequence id or temporal stamp to avoid synchronizing old state after new or some in memory class that can use blocking queue to prioritize events. I resigned from it for simplicity.