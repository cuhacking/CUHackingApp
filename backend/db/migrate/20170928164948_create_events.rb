class CreateEvents < ActiveRecord::Migration[5.1]
  def change
    create_table :events do |t|      
      t.date :date
      t.time :start_time
      t.time :end_time
      t.string :type
      t.string :description
      t.string :name

      t.timestamps
    end
  end
end
