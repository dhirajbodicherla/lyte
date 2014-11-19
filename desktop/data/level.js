{
    list: [{
        mName: "Level 1",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mTarget: {
            id: "Target",
            type: "S",
            shape: "circle",
            x: 590,
            y: 430,
            r: 40
        },
        mSource: {
            id: "Source",
            type: "K",
            shape: "rect",
            x: 1,
            y: 1,
            w: 1,
            h: 1,
            angle: 90
        },
        mMirrors: [{
            id: "1",
            type: "S",
            shape: "rect",
            x: 2,
            y: 2,
            w: 1,
            h: 1,
            angle: 90,
            fixedRotation: 1,
            fixedPosition: 1
        }]
    }, {
        mName: "Level 2",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mTarget: {
            id: "Target",
            type: "S",
            shape: "circle",
            x: 590,
            y: 450,
            r: 40
        },
        mSource: {
            id: "Source",
            type: "K",
            shape: "rect",
            x: 50,
            y: 30,
            w: 40,
            h: 10
        },
        mMirrors: [{
            id: "1",
            type: "S",
            shape: "rect",
            x: 30,
            y: 450,
            w: 50,
            h: 10,
            fixedRotation: 1,
            fixedPosition: 1
        }]
    }, {
        mName: "Level 6",
        mBaseWidth: 640,
        mBaseHeight: 480,
        mTarget: {
            id: "Target",
            type: "S",
            shape: "circle",
            x: 590,
            y: 460,
            r: 20
        },
        mSource: {
            id: "Source",
            type: "K",
            shape: "rect",
            x: 1,
            y: 1,
            w: 1,
            h: 1,
            angle: 90
        },
        mMirrors: [{
            id: "1",
            type: "S",
            shape: "rect",
            x: 2,
            y: 2,
            w: 1,
            h: 1,
            fixedRotation: 1,
            fixedPosition: 1
        }, {
            id: "2",
            type: "S",
            shape: "rect",
            x: 3,
            y: 3,
            w: 1,
            h: 1,
            fixedRotation: 0,
            fixedPosition: 1
        }],
        mAsteroids: [{
            id: "1",
            type: "S",
            shape: "circle",
            x: 1,
            y: 4,
            r: 3
        }, {
            id: "2",
            type: "S",
            shape: "circle",
            x: 1,
            y: 5,
            r: 3
        }],
        mBlackholes: [{
            id: "1",
            type: "S",
            shape: "circle",
            x: 3,
            y: 3,
            r: 3,
            ir: 30,
            g: 4
        }, {
            id: "2",
            type: "S",
            shape: "circle",
            x: 4,
            y: 4,
            r: 3,
            ir: 10,
            g: 2
        }]
    }]
}