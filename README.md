# Log cconsumer


## Summary
This is simple jar that listen for dedicated file and publish incoming lines to activemq channel

## Usage
`java - jar buuild/libs/log_consumer-0.1.0.jar --log.path=%path to file&%--jms.url=%broker url% --jms.destionation=%destination%`
Or set this params directly in application.yaml and compile it by yourself =)