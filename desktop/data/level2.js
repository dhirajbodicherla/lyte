

{
    list: [
    {
        mName: "Level 1",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mTarget:{id:"Target", type:"S", shape:"circle", x:300, y:200, r:40},
        mSource:{id:"Source", type:"K", shape:"rect", x:-300, y:-200, w:40, h:10, angle: 90},
        mMirrors: [{id:"1",type:"S",shape:"rect",x:-300,y:200,w:50,h:10, angle:45, fixedRotation:1, fixedPosition:1}] 
    }, 


    {
        mName: "Level 6",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mTarget:{id:"Target", type:"S", shape:"circle", x:300, y:200, r:20},
        mSource:{id:"Source", type:"K", shape:"rect", x:-300, y:-200, w:40, h:10},
        mMirrors: [{id:"1",type:"S",shape:"rect",x:200,y:-100,w:50,h:10,fixedRotation:1, fixedPosition:1}, 
                   {id:"2",type:"S",shape:"rect",x:-100,y:-200,w:50,h:10,fixedRotation:0, fixedPosition:1}], 
        mAsteroids: [{id:"1",type:"S",shape:"circle",x:-100,y:200,r:20}, 
                   {id:"2",type:"S",shape:"circle",x:-150,y:-50,r:20}],
        mBlackholes: [{id:"1",type:"k",shape:"circle",x:200,y:40,r:20, ir: 100, g:4}, 
                   {id:"2",type:"k",shape:"circle",x:-250,y:60,r:10, ir: 150, g:2}]
    } 
    ]
}
