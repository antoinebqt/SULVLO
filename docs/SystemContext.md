![](https://www.plantuml.com/plantuml/png/TL31Rjim3BtxAxYSKc2BBpljrABeBSE2PTcrO6Gk5a9HXacbzTzFhXelJhER7lNUu-ExLyFUKk3ganV7gI5epJhzLjSzlgmEtjgqJqgzYs8ajd8nr4VVkrQjhH-_VjamYks-hsjwpVyDDNdu_9Z96Q-wqRWoRqom7TGemFiND1uFFGRu4tlOxjQ_rp-gQaEzHbcgvHXY3vzXiItFHNwkyOGCE_6dpF8sWBx_tPrrHguLxpnAfl-Qm2Wk6u12nt4Wofggv5bguXF70luH2nwL3ypnHU5_snKiWlLu8eRz07ll1iS4KS1Xw9AkygfYzlpqQaiAw7bQzZGYq6dNdDlX44XiOcyAdlEhdyGNXLrTKu8fUCd9Zc-nhANBCyfT9IiazN9Ot070Q3lhyyeuAUcRLGa3dDqBBb40fO60WeVCKqFpKJxupptdrIX9esLsbjtGN5SKy4XwdivqC_Ufh_aUYxV8boryfD_dGGhy5m00)

@startuml
!include https://raw.githubusercontent.com/kirchsth/C4-PlantUML/extended/C4_Context.puml

title System Context diagram for SULVLO

Person(studentA, "Student", "Laval University student")
Person(technician, "Technician", "SULVLO employee")

System(sulvloSystem, "SULVLO System", "Allows Laval University students to travel by bicycle on campus.")
System_Ext(emailSystem, "Email system", "")
System_Ext(paymentSystem, "Payment system", "")

Rel(studentA, sulvloSystem, "travels in bike on the University Laval campus using")   
Rel(technician, sulvloSystem, "uses the system like the student and manages stations using")   
Rel(sulvloSystem, emailSystem, "sends email using")
Rel(sulvloSystem, paymentSystem, "makes payment using")
Rel(emailSystem, studentA, "sends email to")
Rel(emailSystem, technician, "sends email to")
@enduml
