[![Build Status](https://travis-ci.org/OnapleRPG/Brawlator.svg?branch=master)](https://travis-ci.org/OnapleRPG/Brawlator)
![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=Brawlator&metric=alert_status)
# Introduction
Brawlator is a **sponge** plugin. It provide a system able to create and manage **customs** monsters based on **minecraft entity**. Monsters can be created by in game **command lines** and are stored in a **configuration file**.

This plugin can also manage the spawn of these monsters with custom mob spawner. 


# Getting started
## installation
Donwload the `.jar` file and place it in the _Mods_ folder of your server.
## fonctionnement
With the command ```/monster <custom_name> <type>``` you can create a monster. Now the freshly born monster is stored, you can spawn him with the command ```/invoke <monster_name>```.

The second important element of this plugin is the spawner component. It can spawn creatures around it. To create a simple spawner just type ```/spawner <x><y><z><monstername>```
This will create a *barrier block* at the specified position And periodicly spawn a monster when the spawntime come to 0. 

## Customisation
### Monster
#### Stats
_Arriving soon_
#### Effects
Several effects can be added to monsters.   
* ```RESISTANCE```
* ```FIRE_RESISTANCE```
* ```INVISIBILITY```
* ```REGENERATION```  

To add effects to a monster just type this command : ```/effect <monster_name> <effect> [duration] [amplifier] ```. The monster name must be renseigned and it refers to the monster you want to add effect.  
The effect is the effect you want to apply.   
Duration is how long the effect will be effective on the spawned monster default value is *99999* and amplifier is the power of the effect, the default value is *1*.
#### Equipement
Some monsters like *zombie* or *skeleton* can be equiped with stuff. To equipe monster with stuff type  the following command : ```/equipment <monster_name> <emplacement > <equipment >```
#### Configuration file
sample of configuration file :  
``` Monster[  
{  
    damage=1.0  
    effects=[]  
       equipement {  
            hand {  
                modifierId=0  
                name=BOW  
                }  
            }  
       hp=50.0  
       knockbackResistance=1  
       name="archer violant"  
       speed=1.0  
       type=skeleton  
    }  
] ```
