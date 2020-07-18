# Brawlator ![Build Status](https://travis-ci.org/OnapleRPG/Brawlator.svg?branch=master) ![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=Brawlator&metric=alert_status) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Project discontinued
We decided to entirely redo Brawlator from scratch! [Checkout the new one](https://github.com/OnapleRPG/Brawlator)!

## Introduction
Brawlator is a **sponge** plugin. It provide a system able to create and manage **customs** monsters based on **minecraft entity**. Monsters can be created by in game **command lines** and are stored in a **configuration file**.

This plugin can also manage the spawn of these monsters with custom mob spawner. 


## Getting started
### installation
Donwload the `.jar` file and place it in the _Mods_ folder of your server.
### fonctionnement
monster are created in configuration file and they can be summoned in the real world with the command ```/invoke <monster_name>```.

The second important element of this plugin is the spawner component. It can spawn creatures around it. To create a simple spawner just type ```/spawner <x><y><z><monstername>```
This will create a *barrier block* at the specified position And periodicly spawn a monster when the spawntime come to 0. 

## Customisation
### Monster
#### Attributes
 You can edit monster attributes to customize their power and behaviour.
 * The **type** of the monster. it's mandatory and it take a `EntityType` like zombie,skelton ,etc .. a complete list of monster is aviable [here](https://minecraft.gamepedia.com/Mob)
 * Monster **display name** is a mandatory parameter,it used to identify custom monsters so it must be unique. Whereas it a `String` d space are allowed. 
 * the **speed** at the monster walk, the default is 1 and it take a `Double`
 * **health points** is the monster quantity of health, the default is 20. it take only `Integer`
 * **knockback resistance** is the resitance of attack knockback. it take an `Integer`
 

 

#### Effects
All minecraft potion's effects can be applied to monsters. when the monster appear, effect are active for the maximum *duration*.
about the potion *amplifier* you can indicate any positive integer. the most common appliable effects are :
* ```RESISTANCE```
* ```FIRE_RESISTANCE```
* ```INVISIBILITY```
* ```REGENERATION```   
see the complete list of effect [here](https://minecraft.gamepedia.com/Status_effect).
to use effects in configguration see the the sample of configurattion below 
 ```
effects = [
    {
        type = minecraft:speed
        amplifier = 5 # this parameter is optionnal (default 1)
    },
    {
        type = minecraft:strength
        amplifier = 2 
    }
] 
```  
#### Equipement
Some monsters like *zombie* or *skeleton* can be equiped with stuff.
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
] 
```
  
### Spawner 

To automatically invoke monster in your world, you can use spawners. They're defined by coordinates and a kind of monster. Then it will periodically summon a new monster. 
In fact there are caracteristics :
* coordinates of the spawner
* the monster name
* the spawn radius in blocks (default : 20)

* the spawn rate in minute (default :5)
* the quantity (default : 5)

#### configuration
