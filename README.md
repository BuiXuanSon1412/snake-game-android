# Simplified Snake Game

## Introduction
    1. Description: Classic Snake Game is built on Kotlin libraries.
    2. Aim: educational purposes 
    3. Focus: simplified rendering (optimally) and scalability (still got some broken minor functionalities)

## Description
### Screen Captures    
    - Images of screen.
### Game Play
    - Currently including fixed 3 map selections.
    - Snake collided with wall or itself would die.
    - Snake eats food to get score and Game gets harder when Snake lengthen.
    - Pause in-game, resting or setting based on users.
### Project Structure
#### Brief    
    - Separate into 3 packages
        - components (Activity, Fragment): layer between user interface with application data and logic.
        - animations (View): custom View rendering animations
        - engine (objects): game engine taking on logic of the game
    - Relations:
        - components including event handlers which trigger state of data in engine
        - animation belonging to components, which also get updates from engine.
    - Techniques:
        - collision detection:
            - square detection:
                show your logic of detection
        - design pattern:
            - data binding: manage component from Activity
            - observer: update in-game score
        - concurrency:
            - coroutine: using to render animation

#### Details
    - File System
    - Package 'animations'
    - Package 'components'
    - Package 'engine'
        