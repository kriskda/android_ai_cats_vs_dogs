# Dogs and cats classification
Sample project with a goal to build and train CNN model and export it to tensorflow lite model to use with the sample android app. It contains:
- `jupyter/ai_model.ipynb` - dataset preparation, model optimization and training
- `jupyter/prepare_data.ipynb` - nto really needed, just to see train/validation images
- `DogsAndCatsAndroid` - android sample app. It is missing `*.tflite` model in `data/src/main/ml/` directory. See notes below 

https://user-images.githubusercontent.com/2589087/215436089-d66059c0-75c3-4a1c-908a-acdd00321b33.mov


Important notes:
- Due to size constraints dataset, `*.h5` and `*.tflite` models not provided in this repository
- There was no motivation to produce nice UI experience. It should just work
- CNN model was built and trained using tensorflow framework. Validation accuracy reached around 88%


![2023-01-30 10 11 43 github com af2b4a2a37de](https://user-images.githubusercontent.com/2589087/215434973-e0f08172-c053-4418-8a27-1533c75ec722.png)


CNN model architecture. Some parameter values were tuned using keras tuner.


![2023-01-30 10 20 16 netron app d6755a2ef91d](https://user-images.githubusercontent.com/2589087/215436892-579070ab-2205-4432-9cc6-9ef44ab63dc7.png)
