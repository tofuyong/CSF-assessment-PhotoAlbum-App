export class Bundle {
    constructor(
        public bundleId: string,
        public name: string,
        public title: string,
        public comments: string,
        public date: Date,
        public imageUrls: string[] 
    ) { };
}