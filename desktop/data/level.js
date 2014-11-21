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
            x: 0,
            y: 0,
            w: 0.5,
            h: 0.5,
            angle: 90
        },
        mMirrors: [{
            id: "1",
            type: "S",
            shape: "rect",
            x: 5,
            y: 5,
            w: 0.5,
            h: 0.5,
            fixedRotation: 1,
            fixedPosition: 1
        }, {
            id: "2",
            type: "S",
            shape: "rect",
            x: -5,
            y: 1,
            w: 0.5,
            h: 0.5,
            fixedRotation: 0,
            fixedPosition: 1
        }],
        mAsteroids: [{
            id: "1",
            type: "S",
            shape: "circle",
            x: 5,
            y: -5,
            r: 1
        }, {
            id: "2",
            type: "S",
            shape: "circle",
            x: -3,
            y: -3,
            r: 1
        }],
        mBlackholes: [{
            id: "1",
            type: "S",
            shape: "circle",
            x: -8,
            y: -6,
            r: 1,
            ir: 1,
            g: 4
        }, {
            id: "2",
            type: "S",
            shape: "circle",
            x: -6,
            y: 4,
            r: 1,
            ir: 1,
            g: 2
        }]
    }]
}