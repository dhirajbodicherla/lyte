{
    list: [{
        mName: "Level 1",
        mBaseWidth: 1,
        mBaseHeight: 1,
        mTarget: {
            id: "Target",
            type: "S",
            shape: "circle",
            x: 50,
            y: 40,
            r: 4
        },
        mSource: {
            id: "Source",
            type: "K",
            shape: "rect",
            x: -10,
            y: -10,
            w: 5,
            h: 2,
            angle: 90
        },
        mMirrors: [{
            id: "1",
            type: "S",
            shape: "rect",
            x: 10,
            y: 10,
            w: 2,
            h: 5,
            angle: 0,
            fixedRotation: 1,
            fixedPosition: 1
        }]
    }, {
        mName: "Level 2",
        mBaseWidth: 1,
        mBaseHeight: 1,
        mTarget: {
            id: "Target",
            type: "S",
            shape: "circle",
            x: 50,
            y: 40,
            r: 4
        },
        mSource: {
            id: "Source",
            type: "K",
            shape: "rect",
            x: -10,
            y: -10,
            w: 5,
            h: 2
        },
        mMirrors: [{
            id: "1",
            type: "S",
            shape: "rect",
            x: 10,
            y: 10,
            w: 2,
            h: 5,
            fixedRotation: 1,
            fixedPosition: 1
        }]
    }, {
        mName: "Level 6",
        mBaseWidth: 1,
        mBaseHeight: 1,
        mTarget: {
        	id: "Target",
            type: "S",
            shape: "circle",
            x: 50,
            y: 40,
            r: 4
        },
        mSource: {
        	id: "Source",
            type: "K",
            shape: "rect",
            x: -10,
            y: -10,
            w: 5,
            h: 2,
            angle: 90
        },
        mMirrors: [{
            id: "1",
            type: "S",
            shape: "rect",
            x: 10,
            y: 10,
            w: 2,
            h: 5,
            fixedRotation: 1,
            fixedPosition: 1
        }, {
            id: "2",
            type: "S",
            shape: "rect",
            x: 15,
            y: 20,
            w: 2,
            h: 5,
            fixedRotation: 0,
            fixedPosition: 1
        }],
        mAsteroids: [{
            id: "1",
            type: "S",
            shape: "circle",
            x: -20,
            y: -20,
            r: 3
        }, {
            id: "2",
            type: "S",
            shape: "circle",
            x: -30,
            y: -30,
            r: 3
        }],
        mBlackholes: [{
            id: "1",
            type: "S",
            shape: "circle",
            x: 30,
            y: 30,
            r: 3,
            ir: 30,
            g: 4
        }, {
            id: "2",
            type: "S",
            shape: "circle",
            x: 40,
            y: 40,
            r: 3,
            ir: 10,
            g: 2
        }]
    }]
}