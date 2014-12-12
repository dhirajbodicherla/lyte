

{
    list: [
    {
        mName: "Level 1",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mTarget:{name:"e", type:"S", shape:"circle", x:590, y:460, r:40},
        mSource:{name:"s", type:"K", shape:"rect", x:30, y:30, w:40, h:10, angle: 90},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:30,y:300,w:60,h:20, angle:45, fixedRotation:1, fixedPosition:1}] 
    }, 


    {
        mName: "Level 6",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mTarget:{name:"e", type:"S", shape:"circle", x:590, y:460, r:20},
        mSource:{name:"s", type:"K", shape:"rect", x:40, y:20, w:40, h:10},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:500,y:100,w:50,h:10,fixedRotation:1, fixedPosition:0}, 
                   {name:"m",type:"S",shape:"rect",x:100,y:400,w:50,h:10,fixedRotation:0, fixedPosition:1}], 
        mAsteroids: [{name:"a",type:"S",shape:"circle",x:100,y:200,r:20}, 
                   {name:"a",type:"S",shape:"circle",x:400,y:300,r:20}],
        mBlackholes: [{name:"b",type:"k",shape:"circle",x:200,y:400,r:20, ir: 100, g:4}, 
                   {name:"b",type:"k",shape:"circle",x:300,y:300,r:10, ir: 150, g:2}]
    } 
    ]
}
