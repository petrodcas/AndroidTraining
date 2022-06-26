Index
=====

1. [English](#Summary)
2. [Español](#Resumen)
3. [Links](#Links)

Summary
========

This project tests ViewFlipper by swapping its animations based on gestures.

A ViewFlipper is added into fragment_main.xml (layout) with four children views.

These views are iterated based on gestures (left/top/right/bottom and diagonals). 

Different animations are set to the ViewFlipper according to the gesture's movement's direction.


ViewFlipper
===========

it's quite an useful View to handle animations when swapping between views. 

Child elements can be manually iterated by using "_showNext()_" and "_showPrevious()_" methods or automatically
through "_startFlipping()_" and "_setFlipInterval()_".

The ViewFlipper has to be inserted as an element in a layout. Any children view
added to it will be treated as an individual element to be iterated.

Animations are break into two: An "InAnimation" (that is, the animation that will be played when 
the child element is put as shown) and an "OutAnimation" (that is, the animation that will be played
when the currently shown child element is put to hide).


_ _ _

Resumen
=======

Este proyecto prueba ViewFlipper cambiando sus animaciones según gestos.

Un ViewFlipper se añade en fragment_main.xml (layout) con cuatro vistas hijas.

Estas vistas son iteradas según gestos (izquierda/derecha/abajo/arriba y diagonales).

Se establecen diferentes animaciones al ViewFlipper dependiendo de la dirección del movimiento 
de los gestos.


ViewFlipper
==========

Es una vista muy útil para manejar animaciones al cambiar entre vistas.

Los elementos hijos pueden ser iterados manualmente usando los métodos "_showNext()_" y "_showPrevious()_",
o bien, automáticamente a través de  "_startFlipping()_" y "_setFlipInterval()_".

El ViewFlipper se debe insertar como un elemento en el layout. Cualquier vista hija que se le añada
será tratada como un elemento individual para iterar.

Las animaciones se separan en dos: Una animación de entrada o _InAnimation_ (es decir, la animación 
que se reproducirá cuando un elemento hijo pase a ser mostrado) y una animación de salida o _OutAnimation_
(es decir, la animación que se reproducirá cuando el elemento hijo actualmente mostrado sea ocultado).

_ _ _

Links
=====

1. [ViewFlipper](https://developer.android.com/reference/android/widget/ViewFlipper)
2. [GestureDetector](https://developer.android.com/reference/android/view/GestureDetector)  (was not used, but it's useful to know it to handle touch events)
