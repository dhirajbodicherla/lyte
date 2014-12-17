

{
    list: [


     {
        mName: "Level 1",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:80, y:80, w:40, h:10, angle: 30},
        mTarget:{name:"e", type:"S", shape:"circle", x:450, y:300, r:40},
    }, 


    {
        mName: "Level 2",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:80, y:80, w:40, h:10, angle: 50},
        mTarget:{name:"e", type:"S", shape:"circle", x:500, y:200, r:40},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:350,y:400,w:90,h:25, angle:0, fixedRotation:0, fixedPosition:0}] 
    }, 

    {
        mName: "Level 3",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:300, y:40, w:50, h:10, angle: 150},
        mTarget:{name:"e", type:"S", shape:"circle", x:450, y:300, r:40},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:85,y:150,w:90,h:25, angle:145, fixedRotation:1, fixedPosition:0}] 
    }, 

   {
        mName: "Level 4",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:80, y:60, w:60, h:15, angle: 75},
        mTarget:{name:"e", type:"S", shape:"circle", x:570, y:350, r:40},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:500,y:300,w:90,h:25, angle:20, fixedRotation:0, fixedPosition:1}, 
                   {name:"m",type:"S",shape:"rect",x:400,y:130,w:90,h:25, angle:45, fixedRotation:1, fixedPosition:0}], 
    },

    {
        mName: "Level 5",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:80, y:80, w:40, h:10, angle: 35},
        mTarget:{name:"e", type:"S", shape:"circle", x:480, y:440, r:50},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:70,y:400,w:90,h:25, angle:120, fixedRotation:1, fixedPosition:0},
                   {name:"m",type:"S",shape:"rect",x:340,y:50,w:90,h:25, angle:90, fixedRotation:0, fixedPosition:1}],
        mAsteroids: [{name:"a",type:"k",shape:"circle",x:400,y:300,r:30}], 
    },

    {
        mName: "Level 6",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:80, y:80, w:40, h:10, angle:25},
        mTarget:{name:"e", type:"S", shape:"circle", x:320, y:470, r:40},
        mBlackholes: [{name:"b",type:"k",shape:"circle",x:200,y:300,r:12, ir: 150, g:2}]
    },

    {
        mName: "Level 7",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:80, y:80, w:40, h:10, angle:15},
        mTarget:{name:"e", type:"S", shape:"circle", x:590, y:460, r:40},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:300,y:400,w:90,h:25, angle:45, fixedRotation:1, fixedPosition:0}],
        mBlackholes: [{name:"b",type:"k",shape:"circle",x:300,y:250,r:15, ir: 120, g:2}]
    },

  {
        mName: "Level 8",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:60, y:80, w:40, h:10, angle:55},
        mTarget:{name:"e", type:"S", shape:"circle", x:400, y:460, r:40},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:250,y:90,w:90,h:25, angle:100, fixedRotation:1, fixedPosition:1}],
        mBlackholes: [{name:"b",type:"k",shape:"circle",x:250,y:200,r:10, ir: 100, g:2},
                      {name:"b",type:"k",shape:"circle",x:430,y:300,r:10, ir: 75, g:2}]
    },

    {
        mName: "Level 9",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:50, y:80, w:40, h:10, angle:90},
        mTarget:{name:"e", type:"S", shape:"circle", x:600, y:240, r:40},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:300,y:350,w:90,h:25, angle:45, fixedRotation:1, fixedPosition:1},
                    {name:"m",type:"S",shape:"rect",x:500,y:220,w:90,h:25, angle:90, fixedRotation:1, fixedPosition:1},
                    {name:"m",type:"S",shape:"rect",x:250,y:90,w:90,h:25, angle:-40, fixedRotation:1, fixedPosition:1}],
        mBlackholes: [{name:"b",type:"k",shape:"circle",x:100,y:200,r:7, ir: 55, g:2},
                      {name:"b",type:"k",shape:"circle",x:420,y:250,r:5, ir: 65, g:2}]
    },

    {
        mName: "Level 10",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:80, y:80, w:40, h:10, angle:25},
        mTarget:{name:"e", type:"S", shape:"circle", x:590, y:460, r:40},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:550,y:100,w:90,h:25, angle:65, fixedRotation:1, fixedPosition:0}, 
                   {name:"m",type:"S",shape:"rect",x:200,y:300,w:90,h:25, angle:0, fixedRotation:0, fixedPosition:1}], 
        mAsteroids: [{name:"a",type:"k",shape:"circle",x:100,y:200,r:20}],
        mBlackholes: [{name:"b",type:"k",shape:"circle",x:320,y:240,r:5, ir: 100, g:4}]
    }

    ]
}
