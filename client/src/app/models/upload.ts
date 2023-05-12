export class Upload {
    constructor(
        public name: string,
        public title: string,
        public comments: string,
        public archive: Blob 
    ) { };
}