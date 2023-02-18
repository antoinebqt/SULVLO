![](https://www.plantuml.com/plantuml/png/jLV1RYCt3BtFLwW-PO5il0LwwcdJT8ico3R6scwFWQnX4Y4QZIffl3MA_dkfKKI3bZ7m9jXBmY7VU-H890V-6ABqSUXCyuEsoWmjYASONVXvFlVowyMZZa_3PWZWLMyZs7YX-cxkZBI9ypx4rkZD_EgdzulaMd--dNSoHF3ekhz2XjGM_8NxJlgTworoiduJdhLrqijEU6cV0pZnuuSFJHDrD20874IhvMD2FVHUBDUtVzxUDKsp01zwUvOgoUQvc2tZq29ZXkQjt4eZrbPlCQZZJeINsBl2ZA2Uh5PQMkAlo9KaSYe1dJFz3W2Pors8qDr_-YUUERdhK2vx4Bp8poACZaJdUEYaDeJ-b9uujcde-Ez_wGVRIhyxaywTYxzW8owTCrh9g7ihBXVNx_vj6ZgWCpd4fz-qYU3FnINQw2lOufxTW0-m4nx-7Y1WcU3nOCJ36CRiUp47ljCX83aKycBlUflWyb0D7Lkj89tVtSqTqlQyyMQ_G3WNQpG4MltW5L1cyY2Bar0qasfv3ioeOz7evHPCYgt8EeAYTa4SsJMIkkNAQFnxBagpbEVP3NQhkBOH_8DKmCuXTuwsZ-DhvSQftukZYsv6dvGb39lni9V39YYlNUgUwKDdNAo8SwiQEPw3Zzv_nE1OqWgDwL8u2JDdqbGDgc_HlxQcLyzNoIOC5yjAXmMIH6vGKfakarEnE4QTceKGnt7EDvJjqr8H5TM9UYJL5p1iZPHsc0LGWuU0c4C8tSy4Xew8OuujcSCrCHl259oo4BJEJgV62yXlI-ib6LL6SYO3Gyg3Z7Kqxub91Es52hCd7wo7MIUV8HHlm1fm_CijbPl1_rNLsJV6ADjXZDO9aOioHGX61GMmRSW-NWqrGJAgUiYfyeHjfT6jZ7Ky6QpY56PT_s9NASW_-MhKXyceD8gpbFa85hoCB3wHeFclERw0BvtcErYWP_BpG2p-XU2yFV7NpKiRk_xQVeQkzplwNBe-w8WENFWYXqY12PGkv2AhxFqc8Uhq0ododov6Ly-KQFDDoTJZoqLc7cGRn8FlEo5jAxvw7I68s1-VxTVJwRLF5w11VLL-6gbNqgjvolRfzJE1Dvq1spvyelZe7cpEQf3O6c0_5ih-BzjMEYsKD6Oi0xqtoxi_vh-lLWiaSoxx-DHqRT6lm4LenMPtJ6f_BAygfbPuUqdrmR4VEASMLcs_yVaRD3w2ROVE_0y0)

@startuml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Container.puml
!include https://raw.githubusercontent.com/plantuml-stdlib/C4-PlantUML/master/C4_Component.puml

skinparam ranksep 100

title Components diagram for SULVLO


Person(userPerson, "Student", "Laval University student")
Person(technicianPerson, "Technician", "SULVLO employee")
System_Ext(paymentSystem, "Payment system", "")
System_Ext(emailSystem, "Email system", "")


Container_Boundary(app, Web Application API){

Component(authFilter, Authentication Filter, "Jersey request server filter")
Component(permissionsFilter, Permissions Filter, "Jersey request server filter")
Component(permissionsSer, Permissions Service, "POJO")

Component(userRes, User Resource, "Jersey Resource")
Component(userSer, User Service, "POJO")
Component(user, User, "POJO")
Component(traveler, Traveler, "POJO")
Component(technician, Technician, "POJO")
Component(paymentClient, Payment Client, "Java Interface")

Component(emailingSer, Email Service, "POJO")
Component(emailClient, Email Client, "Java Interface")

Component(subRes, Subscription Resource, "Jersey Resource")
Component(subSer, Subscription Service, "POJO")
Component(sub, Subscription, "POJO")

Component(tripRes, Trip Resource, "Jersey Resource")
Component(tripSer, Trip Service, "POJO")
Component(code, UnlockCode, "POJO")
Component(trip, Trip, "POJO")

Component(codeRes, UnlockCode Resource, "Jersey Resource")
Component(codeSer, UnlockCode Service, "POJO")

Component(stationRes, Station Resource, "Jersey Resource")
Component(stationSer, Station Service, "POJO")

Rel(authFilter, subRes, "secures")
Rel(authFilter, tripRes, "secures")
Rel(authFilter, codeRes, "secures")
Rel(permissionsFilter, permissionsSer, "uses")
Rel(permissionsFilter, authFilter, "uses")
Rel(authFilter, stationRes, "secures")


Rel(userRes, userSer, "uses")
Rel(userSer, user, "creates")
Rel(userSer, traveler, "creates")
Rel(userSer, technician, "creates")
Rel(traveler, paymentClient, "makes payments using")

Rel(subRes, subSer, "uses")
Rel(subSer, sub, "creates")
Rel(subSer, emailingSer, "uses")

Rel(emailingSer, emailClient, "sends email using")

Rel(tripRes, tripSer, "uses")
Rel(tripSer, code, "validates")
Rel(tripSer, trip, "creates")

Rel(stationRes, stationSer, "uses")
Rel(stationSer, emailingSer, "uses")

Rel(codeRes, codeSer, "uses")
Rel(codeSer, code, "generates")
Rel(codeSer, emailingSer, "uses")


}

Component(emailClientApi, Email Client Api, "POJO")
Component(paymentClientApi, Payment Client Api, "POJO")

ComponentDb(userRepoInMemory, User Repository In Memory, "POJO")
ComponentDb(travelerRepoInMemory, Traveler Repository In Memory, "POJO")
ComponentDb(technicianRepoInMemory, Technician Repository In Memory, "POJO")
ComponentDb(stationRepoInMemory, Station Repository In Memory, "POJO")
Rel(userSer, userRepoInMemory, "reads from and writes to")
Rel(userSer, travelerRepoInMemory, "reads from and writes to")
Rel(userSer, technicianRepoInMemory, "reads from and writes to")

Rel(subSer, travelerRepoInMemory, "reads from and writes to")
Rel(tripSer, travelerRepoInMemory, "reads from and writes to")

Rel(stationSer, stationRepoInMemory, "reads from and writes to")
Rel(stationSer, technicianRepoInMemory, "reads from and writes to")

Rel(emailClient, emailClientApi, "uses")
Rel(paymentClient, paymentClientApi, "uses")

Rel(userPerson, userRes, "makes api calls to", "JSON/HTTP")
Rel(userPerson, authFilter, "makes api calls intercepted by", "JSON/HTTP")
Rel(technicianPerson, userRes, "makes api calls to", "JSON/HTTP")
Rel(technicianPerson, authFilter, "makes api calls intercepted by", "JSON/HTTP")
Rel(paymentClientApi, paymentSystem, "makes api calls to", "JSON/HTTP")
Rel(emailClientApi, emailSystem, "makes api calls to", "JSON/HTTP")
@enduml

