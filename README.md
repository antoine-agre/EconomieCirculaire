# Circular Economy

Case study in agent-oriented programming.

[Full subject here](https://github.com/EmmanuelADAM/jade/tree/english/issia23).

## Model

### Agents

<!--
```
@startuml class

class User {
    Product[] products
    int skill
    int money
    int time
}

class RepairCafe {
    int cost
}

class SparePartsStore {
    int stock
    Part part ?
    int cost
}

class SecondHandStore {
    int stock
    Part part ?
    int cost
}

class Distributor {
    int cost
}

interface Product {
    float price
    Part[] parts
}
interface Part {
    
}

class Mouse {
    
}
enum MousePart {
    
}

class Screen {
    
}
enum ScreenPart {
    
}

class CoffeeMachine {
    
}
enum CoffeeMachinePart {
    
}

class WashingMachine {
    
}
enum WashingMachinePart {
    
}

class Dishwasher {
    
}
enum DishwasherPart {
    
}

class VacuumCleaner {
    
}
enum VacuumCleanerPart {
    
}

Product <|.r. Mouse : Implements
Product <|.r. Screen : Implements
Product <|.r. CoffeeMachine : Implements
Product <|.r. WashingMachine : Implements
Product <|.r. Dishwasher : Implements
Product <|.r. VacuumCleaner : Implements

Part <|.r. MousePart
Part <|.r. ScreenPart
Part <|.r. CoffeeMachinePart
Part <|.r. WashingMachinePart
Part <|.r. DishwasherPart
Part <|.r. VacuumCleanerPart


Product *-- "1..4" Part
User o-- "1..n" Product

User -> RepairCafe : visits
User -> SparePartsStore : visits
User -> SecondHandStore : visits
User -> Distributor : visits

@enduml```
-->


![](images/class.png)

---

<!--
```
@startuml sequence

start

:Broken Object;

if (Enough confidence and money?) then (Yes)
    :Repair Cafe;
    if (Fixed?) then (Yes)
        :Done;
else (No)
if (Enough confidence and money?) then (Yes)
    :Second Hand Store;
else (No)
if (Enough confidence and money?) then (Yes)
    :Spare Parts Store;
else (No)
if (Enough money?) then (Yes)
    :Distributor;
else (No)
    stop

@enduml```
-->

![](images/sequence.png)