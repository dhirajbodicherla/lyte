

{
    list: [

	


    {
        mName: "Level 8",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:80, y:80, w:40, h:10, angle:15},
        mTarget:{name:"e", type:"S", shape:"circle", x:350, y:460, r:40},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:300,y:400,w:90,h:25, angle:90, fixedRotation:1, fixedPosition:1}],
        mBlackholes: [{name:"b",type:"k",shape:"circle",x:300,y:250,r:10, ir: 130, g:2},
        				{name:"b",type:"k",shape:"circle",x:300,y:250,r:20, ir: 150, g:2}]
    },



    {
        mName: "Level 7",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mSource:{name:"s", type:"K", shape:"rect", x:80, y:80, w:40, h:10, angle:25},
        mTarget:{name:"e", type:"S", shape:"circle", x:590, y:460, r:20},
        mMirrors: [{name:"m",type:"S",shape:"rect",x:500,y:100,w:50,h:10,fixedRotation:1, fixedPosition:0}, 
                   {name:"m",type:"S",shape:"rect",x:100,y:400,w:50,h:10,fixedRotation:0, fixedPosition:1}], 
        mAsteroids: [{name:"a",type:"k",shape:"circle",x:100,y:200,r:20}, 
                   {name:"a",type:"k",shape:"circle",x:400,y:300,r:20}],
        mBlackholes: [{name:"b",type:"k",shape:"circle",x:200,y:400,r:20, ir: 100, g:4}, 
                   {name:"b",type:"k",shape:"circle",x:300,y:300,r:10, ir: 150, g:2}]
    }

    ]
}
