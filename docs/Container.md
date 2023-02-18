![](https://www.plantuml.com/plantuml/png/RL5DQnin4BtlhvZwv80JKoWKUaeQ0crmwHBRxT6CjHElY3w4HjfqAVtl7LdOniOtpSoRz-OztNF2cB8rpGVjbCaTGPzIu2_pUSJtkvrEVTvcfgYyI-JIdV9s7WowidFBgJDwEt_yTDkMrlh7OcwH4qLfRHvb0xMZU1SAVzCadGp1iStGQTn5jF3g8opNYr-BdqtJoi2xAQVSYTmC9ilwcjmSHebKxxJIw6IwEXO2M8wYRJTVVNOTnd7AsGp62wgIGvrFRk1l0m2dIwOOWg1-qnOUGZ1QOTBUmKFxVIBjPnmGFdwUmJEbD0AwJbwHQIp3Dlf1TyImrMfKOYz57CXejuFNx5JXGQDbJVXIJx0GCWDhfmUXA7s5DcI6GICWl3mjLqLMlFnh3dQU_gGfMTIcrYBwL2hWwaRgQls03JXQ2Uo8RcjzZczUo9n2hlv3Zgf75ZDCbWGSWVEMLTIX-E0PL7DYTZpudSdbzCvG9z1hJZwECv0Z18O6dIB8B7aKuM-hLRiKTH4_-yQgdujq2QbU2aQ_4I1mlN8Vl4M7EsBfuVwawzHxkiiOBBuHbt11eJ4Yv2_MDYzryJnh9jTnxLrNswpFV-Vawt8x-QiR1uDNuVVIoTRy1m00)

@startuml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml


title Containers diagram for SULVLO

Person(student, "Student")
Person(technician, "Technician")
System_Boundary(sulvlo, "SULVLO System") {
    Container(app, "Web Application API", "Java 17, Jetty and Jersey", "Provides bicycle traveling functionality on the Laval University campus via a REST API")
}

System_Ext(emailSystem, "Email system", "")
System_Ext(paymentSystem, "Payment system", "")

Rel(student, app, "purchases semester subscriptions, travels by bicycle, views travel history, pays balance using", "HTTPS")
Rel(technician, app, "uses the system like a student and manages stations using", "HTTPS")
Rel(app, paymentSystem, "makes API calls to", "HTTPS")
Rel_R(app, emailSystem, "sends emails using", "HTTPS")
Rel_U(emailSystem, technician, "sends emails to")
Rel_U(emailSystem, student, "sends emails to")
@enduml
